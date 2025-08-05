//package com.architect.data_service.file_util;
//
////import com.cotiviti.common_app.exceptions.BadRequestException;
////import com.cotiviti.common_app.exceptions.NotFoundException;
////import com.cotiviti.common_app.exceptions.OperationFailedException;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVParser;
//import org.springframework.stereotype.Service;
//
//import java.io.BufferedInputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.AccessDeniedException;
//import java.nio.file.Files;
//import java.nio.file.NoSuchFileException;
//import java.nio.file.Paths;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//
//@Service
//@Slf4j
//public class FileRecordAnalyzer {
////    private static String checkNumericType(String n) {
////        boolean isInteger = Pattern.matches("^[+-]?\\d+$", n);
////        boolean isDecimal = Pattern.matches("^[+-]?\\d+\\.\\d+$", n);
////        String type = "UNKNOWN";
////        if (isInteger) {
////            try {
////                int val = Integer.parseInt(n);
////                if (val >= 0 && val <= Short.MAX_VALUE) {
////                    type = "smallint" + ":0:0";
////                } else {
////                    type = "int" + ":0:0";
////                }
////            } catch (NumberFormatException ex1) {
////                try {
////                    Long.parseLong(n);
////                    type = "bigint" + ":0:0";
////                } catch (NumberFormatException ex2) {
////                    System.out.println("Not Integer");
////                }
////            }
////        } else if (isDecimal) {
////            try {
////                BigDecimal val = new BigDecimal(n);
////                int precision = val.precision();
////                int scale = val.scale();
////                if (precision <= 15) {
////                    type = "float" + ":" + precision + ":" + scale;
////                } else if (precision <= 19 && scale == 4) {
////                    type = "money" + ":" + precision + ":" + scale;
////                } else if (precision <= 38) {
////                    type = "decimal" + ":" + precision + ":" + scale;
////                }
////            } catch (NumberFormatException ex) {
////                System.out.println("Not Decimal");
////            }
////        }
////        return type;
////    }
////
////
////    private static String checkDateTimeType(String n) {
////        HashMap<String, String> regexPattern = new HashMap<>();
////        regexPattern.put("date=yyyy-MM-dd", "^\\d{4}-\\d{2}-\\d{1,2}$");
////        regexPattern.put("date=yyyy.MM.dd", "^\\d{4}\\.\\d{2}\\.\\d{1,2}$");
////        regexPattern.put("date=yyyy/MM/dd", "^\\d{4}/\\d{2}/\\d{1,2}$");
////        regexPattern.put("smalldatetime=yyyy-MM-dd HH:mm", "^\\d{4}-\\d{2}-\\d{1,2} \\d{2}:\\d{2}$");
////        regexPattern.put("smalldatetime=yyyy.MM.dd HH:mm", "^\\d{4}\\.\\d{2}\\.\\d{1,2} \\d{2}:\\d{2}$");
////        regexPattern.put("smalldatetime=yyyy/MM/dd HH:mm", "^\\d{4}/\\d{2}/\\d{1,2} \\d{2}:\\d{2}$");
////        regexPattern.put("datetime=yyyy-MM-dd HH:mm:ss", "^\\d{4}-\\d{2}-\\d{1,2} \\d{2}:\\d{2}:\\d{2}$");
////        regexPattern.put("datetime=yyyy.MM.dd HH:mm:ss", "^\\d{4}\\.\\d{2}\\.\\d{1,2} \\d{2}:\\d{2}:\\d{2}$");
////        regexPattern.put("datetime=yyyy/MM/dd HH:mm:ss", "^\\d{4}/\\d{2}/\\d{1,2} \\d{2}:\\d{2}:\\d{2}$");
////        regexPattern.put("datetime2=yyyy-MM-dd HH:mm:ss.SSSSSS", "^\\d{4}-\\d{2}-\\d{1,2} \\d{2}:\\d{2}:\\d{2}\\.\\d{1,6}$");
////        regexPattern.put("datetime2=yyyy.MM.dd HH:mm:ss.SSSSSS", "^\\d{4}\\.\\d{2}\\.\\d{1,2} \\d{2}:\\d{2}:\\d{2}\\.\\d{1,6}$");
////        regexPattern.put("datetime2=yyyy/MM/dd HH:mm:ss.SSSSSS", "^\\d{4}/\\d{2}/\\d{1,2} \\d{2}:\\d{2}:\\d{2}\\.\\d{1,6}$");
////        Optional<String> key = regexPattern.entrySet().stream()
////                .filter(entry -> Pattern.matches(entry.getValue(), n))
////                .map(Map.Entry::getKey)
////                .findFirst();
////        return key.orElse("UNKNOWN");
////    }
////
////    private static boolean isBitType(String n) {
////        return false;
////    }
////
////    private static List<String> checkCharStringType(List<String> row) {
////        List<String> type;
////        int maxLength = row.stream().mapToInt(String::length).max().orElse(0);
////        boolean allSameLength = row.stream().allMatch(s -> s.length() == maxLength);
////        if (allSameLength) {
////            int chosenLength = maxLength == 0 ? 10 : maxLength;
////            type = List.of("char", String.valueOf(chosenLength));
////        } else {
////            List<Integer> varcharLength = List.of(20, 30, 50, 100, 150, 200, 255, 500, 1000);
////            int chosenLength = varcharLength.stream().filter(length -> length >= maxLength).findFirst().orElse(maxLength);
////            type = List.of("varchar", String.valueOf(chosenLength));
////        }
////        return type;
////    }
//
//
//    public List<List<String>> delimitedFileParser(FileMetaInfo fileMetaInfo, int noOfLinesToProcess, String operation) {
//        CSVFormat.Builder csvFormat = CSVFormat.DEFAULT.builder()
//                .setTrim(true);                                                                             //trim whitespace
//        if (fileMetaInfo.isHasHeader()) {
//            csvFormat.setHeader();                                                                          //automatically detect header
//        }
//        csvFormat.setRecordSeparator(System.lineSeparator());                                               //record separator
//
//
//        if (fileMetaInfo.getFldSep() == null || fileMetaInfo.getFldSep().isEmpty()) {
//            if (operation.equals("filePreview")) {
//                return rawFilePreview(fileMetaInfo.getLocation(), noOfLinesToProcess);
//            } else {
////                log.error("Field separator cannot be empty while populating field metadata.");
////                throw new BadRequestException("Field separator cannot be empty while populating field metadata.");
//            }
//        } else {
//            csvFormat.setDelimiter(fileMetaInfo.getFldSep());                                               //field separator
//        }
//
//
//        if (fileMetaInfo.getFldDelim() != null && !fileMetaInfo.getFldDelim().equals("None")) {             //field delimiter
//            csvFormat.setQuote(fileMetaInfo.getFldDelim().charAt(0));
//        } else {
//            csvFormat.setQuote(null);
//        }
//        String fileLines = readSampleFile(fileMetaInfo.getLocation(), noOfLinesToProcess).getFirst()
//                .stream()
//                .collect(Collectors.joining(System.lineSeparator()));
//        try {
//            CSVParser csvParser = CSVParser.parse(fileLines, csvFormat.get());
//            List<String> headers = new ArrayList<>(csvParser.getHeaderNames());
//            List<List<String>> rows = csvParser.getRecords()
//                    .stream()
//                    .map(record -> Arrays.asList(record.values()))
//                    .collect(Collectors.toList());
//
//
//            if (headers.isEmpty()) {
//                int columnCount = rows.getFirst().size();
//                for (int i = 1; i <= columnCount; i++) {
//                    headers.add("Field" + i);
//                }
//            }
//            rows.addFirst(headers);
//            return rows;
//
//
//        } catch (IllegalArgumentException e) {
////            log.error("Field separator and field delimiter cannot be the same", e); //TODO
////            throw new OperationFailedException("Field separator and field delimiter cannot be the same");
//        } catch (IOException e) {
////            log.error("Couldn't parse file content. Please ensure field separator is correct.", e);
////            throw new OperationFailedException("Couldn't parse file content. Please ensure field separator is correct.");
//        } catch (Exception e) {
////            log.error("Unexpected error occurred. Please ensure the input is correct and try again.", e);
////            throw new OperationFailedException("Unexpected error occurred. Please ensure the input is correct and try again.");
//        }
//    }
//
//
//    private List<List<String>> rawFilePreview(String filePath, int noOfLinesToPreview) {
//        List<List<String>> result = new LinkedList<>();
//        List<List<String>> sampleResult = readSampleFile(filePath, noOfLinesToPreview);
//        List<String> fileLines = sampleResult.getFirst();
//        String recSep = sampleResult.getLast().getFirst();
//        result.add(Collections.singletonList("Flat File Preview"));
//        fileLines.forEach(line -> result.add(Collections.singletonList(
//                line.replaceAll("\t", "<tab>") + "<" + recSep + ">")));
//        return result;
//    }
//
//
////    private List<List<String>> readSampleFile(String filePath, int noOfLines) {
////        int offset = 0;
////        List<String> fileLines = new ArrayList<>();
////        List<List<String>> result = new ArrayList<>();
////        try (Stream<String> lines = Files.lines(Paths.get(filePath));
////             BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
////        ) {
////            lines.filter(line -> !line.trim().isEmpty())
////                    .skip(offset)
////                    .limit(noOfLines)
////                    .forEach(fileLines::add);
////            List<String> fileMetaInfo = new ArrayList<>();
////            try {
//////                fileMetaInfo = getFileInfo(new ArrayList<>(fileLines));
////                fileMetaInfo.add(getRecSeparator(inputStream));
////
////            } catch (Exception e) {
////                System.out.println("sorry, unable to extract file details");
////            }
////            result.add(fileLines);
////            result.add(fileMetaInfo);
////            return result;
////        } catch (NoSuchFileException ex) {
////            log.error("Provided source location is invalid or not found", ex);
//////            throw new NotFoundException("Provided source location is invalid or not found");
////        } catch (AccessDeniedException ex) {
////            log.error("Access denied while reading the file", ex);
//////            throw new OperationFailedException("Access denied while reading the file", ex.getMessage());
////        } catch (IOException ex) {
////            log.error("Error occurred while reading the file", ex);
//////            throw new OperationFailedException("Error occurred while reading the file", ex.getMessage());
////        }
////    }
//
//
////    private List<List<String>> fixedWidthFileParser(FileMetaInfo fileMetaInfo, int maxFileRowsToProcess, String operation) {
////        //TODO: NOT TESTED
////        List<Integer> fixedWidth = Arrays.stream(fileMetaInfo.getFldSep().split(","))
////                .map(str -> {
////                            try {
////                                return Integer.parseInt(str.trim());
////                            } catch (NumberFormatException ex) {
////                                throw new IllegalArgumentException("Invalid Field separator!");
////                            }
////                        }
////                ).toList();
////        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(
////                fileMetaInfo.getLocation()))) {
////            String line;
////            List<String> headers = new ArrayList<>();
////            List<List<String>> rows = new ArrayList<>();
////            while ((line = bufferedReader.readLine()) != null) {
////                List<String> row = new ArrayList<>();
////                int start = 0;
////                if (!line.isBlank()) {
////                    for (int columnWidth : fixedWidth) {
////                        int end = Math.min(start + columnWidth, line.length());
////                        row.add(line.substring(start, end).trim());
////                        start += columnWidth;
////                    }
////                    rows.add(row);
////                }
////                if (rows.size() > maxFileRowsToProcess) break;
////            }
////
////
////            if (fileMetaInfo.isHasHeader()) {
////                return rows;
////            } else {
////                int columnCount = rows.getFirst().size();
////                for (int i = 1; i <= columnCount; i++) {
////                    headers.add("Field" + i);
////                }
////                rows.addFirst(headers);
////                return rows;
////            }
////        } catch (FileNotFoundException e) {
////            throw new NotFoundException("Provided path is invalid!");
////        } catch (IOException e) {
////            throw new OperationFailedException("Failed to extract metadata from the file");
////        }
////    }
//
//
////    private Map<String, List<String>> excelFileParser(FileMetaInfo fileMetaInfo) {
////        //TODO:
////        List<String> headers = new ArrayList<>();
////        List<List<String>> rows = new ArrayList<>();
////        return transformRecord(rows);
////    }
//
//
//    private Map<String, List<String>> transformRecord(List<List<String>> rows) {
//        Map<String, List<String>> transposeRecord = new LinkedHashMap<>();
//        List<String> headers = rows.getFirst();
//        rows.removeFirst();
//        for (int i = 0; i < headers.size(); i++) {
//            System.out.println(i);
//            transposeRecord.put(headers.get(i), new ArrayList<>());
//            for (List<String> row : rows) {
//                transposeRecord.get(headers.get(i)).add(row.get(i));
//            }
//        }
//        return transposeRecord;
//    }
//
//
//    private static String camelCase(String header) {
//        if (!header.contains("_") && !header.contains(" ")) {
//            return header;
//        }
//        String[] subHeader = header.split("[_ ]");
//        StringBuilder camelCaseHeader = new StringBuilder(subHeader[0].toLowerCase());
//        for (int i = 1; i < subHeader.length; i++) {
//            camelCaseHeader.append(subHeader[i].substring(0, 1).toUpperCase())
//                    .append(subHeader[i].substring(1).toLowerCase());
//        }
//        return camelCaseHeader.toString();
//    }
//
//
////    private String checkPrecedence(Set<String> typeSet) {
////        List<String> precedence = new ArrayList<>(List.of(
////                "tinyint", "smallint", "int", "bigint",
////                "float", "money", "decimal",
////                "date", "smalldatetime", "datetime", "datetime2",
////                "char", "varchar"));
////        if ((typeSet.contains("float") && typeSet.contains("money"))) {
////            return "decimal";
////        }
////        return typeSet.stream()
////                .filter(precedence::contains)
////                .max(Comparator.comparingInt(precedence::indexOf))
////                .orElse(null);
////    }
//
//
//    private List<FileRecordInfo> setInHouseMetaData(List<FileRecordInfo> fileRecordInfos, int headerSize, String connectorName) {
//        fileRecordInfos.add(FileRecordInfo.builder().header("Info").srcColumnNo(0).trgColumnNo(headerSize)
//                .trgColumnName("SFileName").trgDataAlias("varchar").trgDataLength(255).trgDataPrecision(0)
//                .trgDataDecimalPlaces(0).trgDataNulls("yes").build());
//        fileRecordInfos.add(FileRecordInfo.builder().header("Info").srcColumnNo(0).trgColumnNo(headerSize + 1)
//                .trgColumnName("SFileDate").trgDataAlias("smalldatetime").trgDataLength(0).trgDataPrecision(0)
//                .trgDataDecimalPlaces(0).trgDataNulls("yes").build());
//        fileRecordInfos.add(FileRecordInfo.builder().header("Info").srcColumnNo(0).trgColumnNo(headerSize + 2)
//                .trgColumnName("AutoId").trgDataAlias("int identity").trgDataLength(0).trgDataPrecision(0)
//                .trgDataDecimalPlaces(0).trgDataNulls("no").build());
//        return fileRecordInfos;
//    }
//
//
//    public List<FileRecordInfo> getFileMetaData(FileMetaInfo fileMetaInfo) {
//        Map<String, List<String>> record = new LinkedHashMap<>();
//        String connectorName = fileMetaInfo.getConnectorName();
//        switch (connectorName) {
//            case "ASCII (Delimited)" -> {
//                record = transformRecord(delimitedFileParser(fileMetaInfo, 100, "fileMetaData"));
//            }
//            case "Unicode (Delimited)" -> {
//                List<List<String>> records = delimitedFileParser(fileMetaInfo, 100, "fileMetaData");
//                List<String> cleanHeader = records.getFirst().stream().map(FileRecordAnalyzer::replaceNonPrintableUnicodeCharacter).toList();
//                records.removeFirst();
//                records.addFirst(cleanHeader);
//                record = transformRecord(records);
//            }
//            case "ASCII (Fixed)" -> {
//                record = transformRecord(fixedWidthFileParser(fileMetaInfo, 100, "fileMetaData"));
//            }
//            case "Excel" -> {
//                record = excelFileParser(fileMetaInfo);
//            }
//        }
//        return inferDataType(connectorName, record);
//    }
//
//
//    private static String replaceNonPrintableUnicodeCharacter(String input) {
//        Matcher matcher = Pattern.compile("[\\p{C}]").matcher(input);
//        return matcher.replaceAll("");
//    }
//
//
//    public List<List<Map<String, String>>> getTabularPreview(FileMetaInfo fileMetaInfo) {
//        List<List<Map<String, String>>> table = new ArrayList<>();
//        switch (fileMetaInfo.getConnectorName()) {
//            case "ASCII (Delimited)", "Unicode (Delimited)" -> {
//                table = prepareTabularData(delimitedFileParser(fileMetaInfo, 10, "filePreview"));
//            }
//            case "ASCII (Fixed)" -> {
//                table = prepareTabularData(fixedWidthFileParser(fileMetaInfo, 10, "filePreview"));
//            }
//        }
//        return table;
//    }
//
//
//    private List<List<Map<String, String>>> prepareTabularData(List<List<String>> record) {
//        List<String> headers = record.removeFirst();
//        List<List<Map<String, String>>> table = new ArrayList<>();
//        List<Map<String, String>> columns = headers.stream().map(item -> {
//            Map<String, String> map = new HashMap<>();
//            map.put("field", item);
//            map.put("header", item);
//            return map;
//        }).toList();
//        List<Map<String, String>> rows = record.stream().map(item -> {
//            Map<String, String> map = new LinkedHashMap<>();
//            for (String header : headers) {
//                map.put(header, item.get(headers.indexOf(header)));
//            }
//            return map;
//        }).toList();
//        table.add(columns);
//        table.add(rows);
//        return table;
//    }
//
//
//    private List<FileRecordInfo> inferDataType(String connectorName, Map<String, List<String>> records) {
//        List<FileRecordInfo> fileRecordInfos = new ArrayList<>();
//        AtomicInteger i = new AtomicInteger(1);
//        records.forEach((header, row) -> {
//            FileRecordInfo fileRecordInfo = FileRecordInfo.builder()
//                    .header("Maps")
//                    .srcColumnNo(i.get())
//                    .srcColumnName(header)
//                    .srcDataAlias("text")
//                    .srcDataLength("999")
//                    .trgColumnNo(i.get())
//                    .trgColumnName(camelCase(header))
//                    .trgDataAlias("varchar")
//                    .trgDataLength(0)
//                    .trgDataPrecision(0)
//                    .trgDataDecimalPlaces(0)
//                    .trgDataNulls(row.contains("") ? "yes" : "no")
//                    .build();
//            i.getAndIncrement();
//
//
//            String type;
//            if (!row.isEmpty()) {
//                Set<String> numType = new HashSet<>();
//                for (String item : row) {
//                    if (item.isEmpty()) continue;
//                    type = checkNumericType(item);
//                    if (type.contains("UNKNOWN")) {
//                        numType.clear();
//                        break;
//                    }
//                    numType.add(type);
//                }
//                if (numType.isEmpty()) {
//                    Set<String> dateTimeType = new HashSet<>();
//                    for (String item : row) {
//                        if (item.isEmpty()) continue;
//                        type = checkDateTimeType(item);
//                        if (type.equals("UNKNOWN")) {
//                            dateTimeType.clear();
//                            break;
//                        }
//                        dateTimeType.add(type);
//                    }
//                    if (dateTimeType.isEmpty()) {
//                        List<String> rawCharType = checkCharStringType(row);
//                        fileRecordInfo.setTrgDataAlias(rawCharType.getFirst());
//                        fileRecordInfo.setTrgDataLength(Integer.parseInt(rawCharType.getLast()));
//                    } else if (dateTimeType.size() == 1) {
//                        String[] rawDateType = dateTimeType.iterator().next().split("=");
//                        fileRecordInfo.setTrgDataAlias(rawDateType[0]);
//                        fileRecordInfo.setTrgDateFormat(rawDateType[1]);
//                    }
//                } else if (numType.size() == 1) {
//                    String[] rawType = numType.iterator().next().split(":");
//                    fileRecordInfo.setTrgDataAlias(rawType[0]);
//                    fileRecordInfo.setTrgDataPrecision(Integer.parseInt(rawType[1]));
//                    fileRecordInfo.setTrgDataDecimalPlaces(Integer.parseInt(rawType[2]));
//                } else {
//                    int precision = 0;
//                    int scale = 0;
//                    Set<String> mixNumType = new HashSet<>();
//                    for (String item : numType) {
//                        String[] rawType = item.split(":");
//                        mixNumType.add(rawType[0]);
//                        precision = Math.max(Integer.parseInt(rawType[1]), precision);
//                        scale = Math.max(Integer.parseInt(rawType[2]), scale);
//                    }
//                    type = checkPrecedence(mixNumType);
//                    fileRecordInfo.setTrgDataAlias(type);
//                    fileRecordInfo.setTrgDataPrecision(precision);
//                    fileRecordInfo.setTrgDataDecimalPlaces(scale);
//                }
//                fileRecordInfos.add(fileRecordInfo);
//            }
//        });
//        return setInHouseMetaData(fileRecordInfos, i.get(), connectorName);
//    }
//
//
//    private static List<String> getFileInfo(List<String> fileLines) throws Exception {
//        HashMap<String, List<List<Integer>>> fldDlms = new HashMap<>();
//        List<String> fieldDelimiters = List.of("'", "\"", "`");
//        String firstRow = fileLines.removeFirst();                                                //remove first row to check header
//        for (String fieldDelimiter : fieldDelimiters) {
//            List<List<Integer>> fldDlm = new ArrayList<>();
//            for (String line : fileLines) {
//                if (line.contains(fieldDelimiter)) {
//                    List<Integer> fieldDlmPos = getFieldDlmPosition(line, fieldDelimiter);
//                    if (fieldDlmPos.size() % 2 == 0) {
//                        fldDlm.add(fieldDlmPos);
//                    } else break;
//                } else break;
//            }
//            fldDlms.put(fieldDelimiter, fldDlm);
//        }
//        String fieldDlm = "None";
//        List<List<Integer>> fieldDlmPos = new ArrayList<>();
//        for (Map.Entry<String, List<List<Integer>>> entry : fldDlms.entrySet()) {
//            if (entry.getValue().size() == fileLines.size()) {
//                fieldDlm = entry.getKey();
//                fieldDlmPos = entry.getValue();
//                break;
//            }
//        }
//        LinkedHashMap<String, List<Integer>> fldSep = new LinkedHashMap<>();
//        String fieldSep = null;
//        List<String> fieldSeparators = List.of(
//                "\"\t\"", "\t",
//                "~|~", "|~|",
//                "||", "~|",
//                "|", ",", ";", "~", "*", "-", "_", "@", "/", "^", "&", "Ç", "ç", "+", "q");
//        if (fieldDlm.equals("None")) {
//            for (String fieldSeparator : fieldSeparators) {
//                List<Integer> count = new ArrayList<>();
//                for (String line : fileLines) {
//                    if (line.contains(fieldSeparator)) {
//                        count.add(countFieldSepFreq(line, fieldSeparator));
//                    }
//                }
//                if (!count.isEmpty()) {
//                    fldSep.put(fieldSeparator, count);
//                }
//            }
//            for (Map.Entry<String, List<Integer>> entry : fldSep.entrySet()) {
//                if (entry.getValue().size() == fileLines.size() && entry.getValue().stream().distinct().count() == 1) {
//                    fieldSep = entry.getKey();
//                    break;
//                }
//            }
//        } else {
//            for (String fieldSeparator : fieldSeparators) {
//                List<Integer> count = new ArrayList<>();
//                int i = 0;
//                for (String line : fileLines) {
//                    if (line.contains(fieldSeparator)) {
//                        count.add(countFieldSepFreq(line, fieldSeparator, fieldDlmPos.get(i)));
//                    }
//                    i++;
//                }
//                if (!count.isEmpty()) {
//                    fldSep.put(fieldSeparator, count);
//                }
//            }
//            for (Map.Entry<String, List<Integer>> entry : fldSep.entrySet()) {
//                if (entry.getValue().size() == fileLines.size() && entry.getValue().stream().distinct().count() == 1) {
//                    fieldSep = entry.getKey();
//                    break;
//                }
//            }
//        }
//        List<String> metaInfo = new ArrayList<>();
//        boolean hasHeader = isHeader(fieldDlm.charAt(0), fieldSep, firstRow);
//        metaInfo.add(String.valueOf(hasHeader));
//        metaInfo.add(fieldDlm);
//        metaInfo.add(fieldSep);
//        return metaInfo;
//    }
//
//
////    private static List<Integer> getFieldDlmPosition(String line, String pattern) {
////        int index = 0;
////        List<Integer> pos = new ArrayList<>();
////        while ((index = line.indexOf(pattern, index)) != -1) {
////            pos.add(index);
////            index += pattern.length();
////        }
////        return pos;
////    }
////
////
////    private static int countFieldSepFreq(String line, String pattern) {
////        int count = 0;
////        int index = 0;
////        while ((index = line.indexOf(pattern, index)) != -1) {
////            count++;
////            index += pattern.length();
////        }
////        return count;
////    }
//
//
////    private static int countFieldSepFreq(String line, String pattern, List<Integer> fieldDlmPos) {
////        int count = 0;
////        int index = 0;
////        while ((index = line.indexOf(pattern, index)) != -1) {
////            final int matchIndex = index;
////            boolean insideRange = false;
////            for (int i = 0; i < fieldDlmPos.size(); i += 1) {
////                int start = fieldDlmPos.get(i);
////                int end = fieldDlmPos.get(i + 1);
////                if (matchIndex >= start && matchIndex <= end) {
////                    insideRange = true;
////                    break;
////                }
////            }
////            if (!insideRange) count++;
////            index += pattern.length();
////        }
////        return count;
////    }
//
//
////    private static boolean isHeader(char fieldDlm, String fieldSep, String firstRow) {
////        if (firstRow == null || firstRow.isBlank()) return false;
////        if (fieldDlm != 'N') {
////            for (String ele : firstRow.split(fieldSep)) {
////                int start = ele.charAt(0) == fieldDlm ? 1 : 0;
////                int end = ele.charAt(ele.length() - 1) == fieldDlm ? ele.length() - 1 : ele.length();
////                ele = ele.substring(start, end);
////                if (ele.matches("^-?\\\\d*(\\\\.\\\\d+)?$") || ele.isBlank() ||
////                        ele.contains(String.valueOf(fieldDlm))) {
////                    return false;
////                }
////            }
////        } else {
////            for (String ele : firstRow.split(fieldSep)) {
////                if (ele.matches("^-?\\\\d*(\\\\.\\\\d+)?$") || ele.isBlank()) {
////                    return false;
////                }
////            }
////        }
////        return true;
////    }
//
//
////    private String getRecSeparator(BufferedInputStream inputStream) {
////        Set<String> separator = new HashSet<>();
////        try {
////            inputStream.mark(1024 * 1024);
////            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
////            int previousChar = -1;
////            int currentChar;
////            int lineBreaksCount = 0;
////            while ((currentChar = reader.read()) != -1 && lineBreaksCount < 10) {
////                if (currentChar == '\n') {
////                    if (previousChar == '\r') {
////                        separator.add("CRLF");
////                    } else {
////                        separator.add("LF");
////                    }
////                    lineBreaksCount++;
////                } else if (previousChar == '\r') {
////                    separator.add("CR");
////                    lineBreaksCount++;
////                }
////                previousChar = currentChar;
////            }
////            inputStream.reset();
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
////        if (separator.isEmpty()) {
////            return "None";
////        } else if (separator.size() == 1) {
////            return separator.iterator().next();
////        } else {
////            throw new RuntimeException("Contains more than one record separator");
////        }
////    }
//}
//
//
