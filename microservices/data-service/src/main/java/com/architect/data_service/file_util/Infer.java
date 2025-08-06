package com.architect.data_service.file_util;

import com.architect.data_service.dto.FileDetailDto;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

@Slf4j
public class Infer {
    public static String cleanInput(String n) {
        if (n == null || n.isEmpty()) {
            return "UNKNOWN";
        }
        return n.trim();
    }

    private static String checkNumericType(String n) {
        n = cleanInput(n);
        if (n.equals("UNKNOWN")) return n;
        boolean isInteger = Pattern.matches("^[+-]?\\d+$", n);
        boolean isDecimal = Pattern.matches("^[+-]?\\d+\\.\\d+$", n);
        String type = "UNKNOWN";
        if (isInteger) {
            try {
                int val = Integer.parseInt(n);
                if (val >= 0 && val <= 255) {
                    type = "tinyint:0:0";
                } else if (val > 255 && val <= Short.MAX_VALUE) {
                    type = "smallint" + ":0:0";
                } else {
                    type = "int" + ":0:0";
                }
            } catch (NumberFormatException ex1) {
                try {
                    Long.parseLong(n);
                    type = "bigint" + ":0:0";
                } catch (NumberFormatException ex2) {
                    log.info("Not Integer", ex2);
                }
            }
        } else if (isDecimal) {
            try {
                BigDecimal val = new BigDecimal(n);
                int precision = val.precision();
                int scale = val.scale();
                if (precision <= 15) {
                    type = "float" + ":" + precision + ":" + scale;
                } else if (precision <= 19 && scale == 4) {
                    type = "money" + ":" + precision + ":" + scale;
                } else if (precision <= 38) {
                    type = "decimal" + ":" + precision + ":" + scale;
                }
            } catch (NumberFormatException ex) {
                log.info("Not Decimal", ex);
            }
        }
        return type;
    }

    private static String checkDateTimeType(String n) {
        HashMap<String, String> regexPattern = new HashMap<>();
        regexPattern.put("date=yyyy-MM-dd", "^\\d{4}-\\d{2}-\\d{1,2}$");
        regexPattern.put("date=yyyy.MM.dd", "^\\d{4}\\.\\d{2}\\.\\d{1,2}$");
        regexPattern.put("date=yyyy/MM/dd", "^\\d{4}/\\d{2}/\\d{1,2}$");
        regexPattern.put("smalldatetime=yyyy-MM-dd HH:mm", "^\\d{4}-\\d{2}-\\d{1,2} \\d{2}:\\d{2}$");
        regexPattern.put("smalldatetime=yyyy.MM.dd HH:mm", "^\\d{4}\\.\\d{2}\\.\\d{1,2} \\d{2}:\\d{2}$");
        regexPattern.put("smalldatetime=yyyy/MM/dd HH:mm", "^\\d{4}/\\d{2}/\\d{1,2} \\d{2}:\\d{2}$");
        regexPattern.put("datetime=yyyy-MM-dd HH:mm:ss", "^\\d{4}-\\d{2}-\\d{1,2} \\d{2}:\\d{2}:\\d{2}$");
        regexPattern.put("datetime=yyyy.MM.dd HH:mm:ss", "^\\d{4}\\.\\d{2}\\.\\d{1,2} \\d{2}:\\d{2}:\\d{2}$");
        regexPattern.put("datetime=yyyy/MM/dd HH:mm:ss", "^\\d{4}/\\d{2}/\\d{1,2} \\d{2}:\\d{2}:\\d{2}$");
        regexPattern.put("datetime2=yyyy-MM-dd HH:mm:ss.SSSSSS", "^\\d{4}-\\d{2}-\\d{1,2} \\d{2}:\\d{2}:\\d{2}\\.\\d{1,6}$");
        regexPattern.put("datetime2=yyyy.MM.dd HH:mm:ss.SSSSSS", "^\\d{4}\\.\\d{2}\\.\\d{1,2} \\d{2}:\\d{2}:\\d{2}\\.\\d{1,6}$");
        regexPattern.put("datetime2=yyyy/MM/dd HH:mm:ss.SSSSSS", "^\\d{4}/\\d{2}/\\d{1,2} \\d{2}:\\d{2}:\\d{2}\\.\\d{1,6}$");
        Optional<String> key = regexPattern.entrySet().stream()
                .filter(entry -> Pattern.matches(entry.getValue(), n))
                .map(Map.Entry::getKey)
                .findFirst();
        return key.orElse("UNKNOWN");
    }

    private static boolean isBitType(String n) {
        return false;
    }

    private static List<String> checkCharStringType(List<String> row) {
        List<String> type;
        int maxLength = row.stream().mapToInt(String::length).max().orElse(0);
        boolean allSameLength = row.stream().allMatch(s -> s.length() == maxLength);
        if (allSameLength) {
            int chosenLength = maxLength == 0 ? 10 : maxLength;
            type = List.of("char", String.valueOf(chosenLength));
        } else {
            List<Integer> varcharLength = List.of(20, 30, 50, 100, 150, 200, 255, 500, 1000);
            int chosenLength = varcharLength.stream()
                    .filter(length -> length >= maxLength)
                    .findFirst()
                    .orElse(maxLength);
            type = List.of("varchar", String.valueOf(chosenLength));
        }
        return type;
    }

    private static String checkPrecedence(Set<String> typeSet) {
        List<String> precedence = new ArrayList<>(List.of(
                "tinyint", "smallint", "int", "bigint",
                "float", "money", "decimal",
                "date", "smalldatetime", "datetime", "datetime2",
                "char", "varchar"));
        if ((typeSet.contains("float") && typeSet.contains("money"))) {
            return "decimal";
        }
        return typeSet.stream()
                .filter(precedence::contains)
                .max(Comparator.comparingInt(precedence::indexOf))
                .orElse(null);
    }

    public static List<FileDetailDto> dataType(String connectorName, Map<String, List<String>> records) {
        List<FileDetailDto> fileDetails = new ArrayList<>();
        AtomicInteger i = new AtomicInteger(1);
        records.forEach((header, row) -> {
            FileDetailDto fileDetail = FileDetailDto.builder()
                    .sourceColumnNo(i.get())
                    .sourceColumnName(header)
                    .sourceDataType("text")
                    .sourceDataLength(999)
                    .targetColumnNo(i.get())
                    .targetColumnName(camelCase(header))
                    .targetDataType("varchar")
                    .targetDataLength(0)
                    .targetDataPrecision(0)
                    .targetDecimalPlaces(0)
                    .targetNullable(row.contains(""))
                    .build();
            i.getAndIncrement();

            String type;
            if (!row.isEmpty()) {
                Set<String> numType = new HashSet<>();
                for (String item : row) {
                    if (item.isEmpty()) continue;
                    type = checkNumericType(item);
                    if (type.contains("UNKNOWN")) {
                        numType.clear();
                        break;
                    }
                    numType.add(type);
                }
                if (numType.isEmpty()) {
                    Set<String> dateTimeType = new HashSet<>();
                    for (String item : row) {
                        if (item.isEmpty()) continue;
                        type = checkDateTimeType(item);
                        if (type.equals("UNKNOWN")) {
                            dateTimeType.clear();
                            break;
                        }
                        dateTimeType.add(type);
                    }
                    if (dateTimeType.isEmpty()) {
                        List<String> rawCharType = checkCharStringType(row);
                        fileDetail.setTargetDataType(rawCharType.getFirst());
                        fileDetail.setTargetDataLength(Integer.parseInt(rawCharType.getLast()));
                    } else if (dateTimeType.size() == 1) {
                        String[] rawDateType = dateTimeType.iterator().next().split("=");
                        fileDetail.setTargetDataType(rawDateType[0]);
                        fileDetail.setTargetDateFormat(rawDateType[1]);
                    }
                } else if (numType.size() == 1) {
                    String[] rawType = numType.iterator().next().split(":");
                    fileDetail.setTargetDataType(rawType[0]);
                    fileDetail.setTargetDataPrecision(Integer.parseInt(rawType[1]));
                    fileDetail.setTargetDecimalPlaces(Integer.parseInt(rawType[2]));
                } else {
                    int precision = 0;
                    int scale = 0;
                    Set<String> mixNumType = new HashSet<>();
                    for (String item : numType) {
                        String[] rawType = item.split(":");
                        mixNumType.add(rawType[0]);
                        precision = Math.max(Integer.parseInt(rawType[1]), precision);
                        scale = Math.max(Integer.parseInt(rawType[2]), scale);
                    }
                    type = checkPrecedence(mixNumType);
                    fileDetail.setTargetDataType(type);
                    fileDetail.setTargetDataPrecision(precision);
                    fileDetail.setTargetDecimalPlaces(scale);
                }
                fileDetails.add(fileDetail);
            }
        });
        return fileDetails;
    }

    private static String camelCase(String header) {
        if (!header.contains("_") && !header.contains(" ")) {
            return header;
        }
        String[] subHeader = header.split("[_ ]");
        StringBuilder camelCaseHeader = new StringBuilder(subHeader[0].toLowerCase());
        for (int i = 1; i < subHeader.length; i++) {
            camelCaseHeader.append(subHeader[i].substring(0, 1).toUpperCase())
                    .append(subHeader[i].substring(1).toLowerCase());
        }
        return camelCaseHeader.toString();
    }
}
