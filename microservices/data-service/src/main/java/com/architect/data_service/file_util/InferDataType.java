package com.architect.data_service.file_util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class InferDataType {
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

    private String checkPrecedence(Set<String> typeSet) {
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

    public static String cleanInput(String n) {
        if (n == null || n.isEmpty()) {
            return "UNKNOWN";
        }
        return n.trim();
    }
}
