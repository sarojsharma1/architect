package com.architect.data_service.file_util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class ExtractMetaData {
    private String getRecSeparator(BufferedInputStream inputStream) {
        Set<String> separator = new HashSet<>();
        try {
            inputStream.mark(1024 * 1024);
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            int previousChar = -1;
            int currentChar;
            int lineBreaksCount = 0;
            while ((currentChar = reader.read()) != -1 && lineBreaksCount < 10) {
                if (currentChar == '\n') {
                    if (previousChar == '\r') {
                        separator.add("CRLF");
                    } else {
                        separator.add("LF");
                    }
                    lineBreaksCount++;
                } else if (previousChar == '\r') {
                    separator.add("CR");
                    lineBreaksCount++;
                }
                previousChar = currentChar;
            }
            inputStream.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (separator.isEmpty()) {
            return "None";
        } else if (separator.size() == 1) {
            return separator.iterator().next();
        } else {
            throw new RuntimeException("Contains more than one record separator");
        }
    }

    private static boolean hasHeader(char fieldDlm, String fieldSep, String firstRow) {
        if (firstRow == null || firstRow.isBlank()) return false;
        if (fieldDlm != 'N') {
            for (String ele : firstRow.split(fieldSep)) {
                int start = ele.charAt(0) == fieldDlm ? 1 : 0;
                int end = ele.charAt(ele.length() - 1) == fieldDlm ? ele.length() - 1 : ele.length();
                ele = ele.substring(start, end);
                if (ele.matches("^-?\\\\d*(\\\\.\\\\d+)?$")
                        || ele.isBlank()
                        || ele.contains(String.valueOf(fieldDlm))) {
                    return false;
                }
            }
        } else {
            for (String ele : firstRow.split(fieldSep)) {
                if (ele.matches("^-?\\\\d*(\\\\.\\\\d+)?$") || ele.isBlank()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int countFieldSepFreq(String line, String pattern, List<Integer> fieldDlmPos) {
        int count = 0;
        int index = 0;
        while ((index = line.indexOf(pattern, index)) != -1) {
            final int matchIndex = index;
            boolean insideRange = false;
            for (int i = 0; i < fieldDlmPos.size(); i += 1) {
                int start = fieldDlmPos.get(i);
                int end = fieldDlmPos.get(i + 1);
                if (matchIndex >= start && matchIndex <= end) {
                    insideRange = true;
                    break;
                }
            }
            if (!insideRange) count++;
            index += pattern.length();
        }
        return count;
    }

    private static List<Integer> getFieldDlmPosition(String line, String pattern) {
        int index = 0;
        List<Integer> pos = new ArrayList<>();
        while ((index = line.indexOf(pattern, index)) != -1) {
            pos.add(index);
            index += pattern.length();
        }
        return pos;
    }

    private static int countFieldSepFreq(String line, String pattern) {
        int count = 0;
        int index = 0;
        while ((index = line.indexOf(pattern, index)) != -1) {
            count++;
            index += pattern.length();
        }
        return count;
    }
}
