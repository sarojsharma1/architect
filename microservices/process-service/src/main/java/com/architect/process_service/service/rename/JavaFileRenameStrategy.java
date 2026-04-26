package com.architect.process_service.service.rename;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class JavaFileRenameStrategy implements FileRenameStrategy {

    //    private static final Logger LOG = LoggerFactory.getLogger(JavaFileRenameStrategy.class);
    private static final Pattern YEAR_PAT = Pattern.compile("\\d{4}\\b");

    @Override
    public String getStrategyName() {
        return "java";
    }

    @Override
    public boolean renameFilesInFolder(String path, String filePattern, int days,
                                       boolean prefix, String dateRule) {
        validateInputs(path, days, dateRule);

        Path root = Paths.get(path);
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern(dateRule));
        String yearPfx = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        String[] patterns = resolvePatterns(filePattern);
        LocalDateTime cutoff = LocalDateTime.now().minusDays(days);
        boolean hasError = false;

//        LOG.info("Scanning '{}' | pattern='{}' | days={} | prefix={} | dateRule={}",
//                path, filePattern, days, prefix, dateRule);

        try (Stream<Path> stream = Files.walk(root)) {
            for (Path file : stream.filter(Files::isRegularFile).toList()) {
                if (shouldSkip(file, cutoff, yearPfx, patterns)) continue;
                Path outPath = buildOutputPath(file, dateStr, prefix);
//                LOG.info("Renaming: {} -> {}", file, outPath);
                if (renameFile(file, outPath)) hasError = true;
            }
        } catch (IOException e) {
//            LOG.error("Failed to walk directory: {}", path, e);
            return true;
        }

//        LOG.info("Completed. hasError={}", hasError);
        return hasError;
    }


    private void validateInputs(String path, int days, String dateRule) {
        validateDateRule(dateRule);
        if (!Files.isDirectory(Paths.get(path)))
            throw new IllegalArgumentException("Not a valid directory: " + path);
        if (days < 1)
            throw new IllegalArgumentException("days must be >= 1, got: " + days);
    }

    private String[] resolvePatterns(String filePattern) {
        return (filePattern == null || filePattern.isBlank())
                ? new String[]{"*.*"} : filePattern.split(",");
    }

    private boolean shouldSkip(Path file, LocalDateTime cutoff,
                               String yearPfx, String[] patterns) throws IOException {
        LocalDateTime lastWrite = Files.getLastModifiedTime(file).toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        String name = file.getFileName().toString();
        String ext = name.contains(".") ? name.substring(name.lastIndexOf('.')) : "";
        String baseName = name.substring(0, name.length() - ext.length());

        return !lastWrite.isBefore(cutoff)
                || !matchesAny(file, name, patterns)
                || name.startsWith(yearPfx)
                || YEAR_PAT.matcher(baseName).find();
    }

    private Path buildOutputPath(Path file, String dateStr, boolean prefix) {
        String name = file.getFileName().toString();
        String ext = name.contains(".") ? name.substring(name.lastIndexOf('.')) : "";
        String baseName = name.substring(0, name.length() - ext.length());
        // prefix=true  → 2026-04_file_test.txt
        // prefix=false → file_test_2026-04.txt
        String newName = prefix ? dateStr + "_" + name : baseName + "_" + dateStr + ext;
        return file.getParent().resolve(newName);
    }

    private boolean renameFile(Path inFile, Path outFile) {
//        LOG.info("[PRE ] in='{}'", inFile);
        try {
            try {
                Files.move(inFile, outFile, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException ex) {
                Files.move(inFile, outFile, StandardCopyOption.REPLACE_EXISTING);
            }
//            LOG.info("[POST] out='{}' code=0 msg='Success'", outFile);
            return false;
        } catch (IOException e) {
//            LOG.error("[POST] code=-1 msg='{}' err='{}'", e.getClass().getName(), e.getMessage());
            return true;
        }
    }

    private boolean matchesAny(Path file, String name, String[] patterns) {
        return Arrays.stream(patterns)
                .anyMatch(p -> file.getFileSystem()
                        .getPathMatcher("glob:" + p.trim())
                        .matches(Paths.get(name)));
    }

    public static void main(String[] args) {
        JavaFileRenameStrategy renamer = new JavaFileRenameStrategy();
        boolean hasError = renamer.renameFilesInFolder(
                "C:/Logs",      // path
                "*.log,*.txt",  // filePattern
                30,             // days
                false,          // prefix → file_test_2026-04.txt
                "yyyy-MM"       // dateRule
        );
        System.out.println(hasError ? "Completed with errors." : "Completed successfully.");
    }
}


/**
 * Strategy interface — defines the contract for file renaming.
 * Any implementation (Java, PowerShell, etc.) must fulfill this.
 */
interface FileRenameStrategy {

    String getStrategyName();

    /**
     * @param path        folder to scan
     * @param filePattern comma-separated globs e.g. "*.log,*.txt" or "" for all
     * @param days        rename files older than N days
     * @param prefix      true = prepend date, false = append date
     * @param dateRule    date format e.g. "yyyyMMdd", "yyyy-MM", "yyyy-MM-dd"
     * @return true if any error occurred
     */
    boolean renameFilesInFolder(String path, String filePattern, int days,
                                boolean prefix, String dateRule);

    /**
     * All supported date format rules
     */
    Set<String> ALLOWED_DATE_RULES = Set.of(
            "yyyyMMdd", "yyyyMM", "yyyy",
            "yyyy-MM-dd", "yyyy-MM",
            "dd-MM-yyyy", "MM-dd-yyyy"
    );

    default void validateDateRule(String dateRule) {
        if (!ALLOWED_DATE_RULES.contains(dateRule))
            throw new IllegalArgumentException(
                    "Invalid dateRule '" + dateRule + "'. Allowed: " + ALLOWED_DATE_RULES);
    }
}