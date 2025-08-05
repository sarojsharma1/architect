//package com.architect.data_service.file_util;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class FileProcessor {
//    public List<FileDetailDto> getFileDetails(FileMetaDataDto fileMetaData) {
//        Map<String, List<String>> record = new LinkedHashMap<>();
//        String connectorName = fileMetaData.getConnectorName();
//        switch (connectorName) {
//            case "ASCII (Delimited)" -> {
//                record = transformRecord(FileParser.delimitedFileParser(fileMetaData, 100, "fileMetaData"));
//            }
//            case "Unicode (Delimited)" -> {
//                List<List<String>> records = FileParser.delimitedFileParser(fileMetaData, 100, "fileMetaData");
//                List<String> cleanHeader = records.getFirst().stream().map(FileProcessor::replaceNonPrintableUnicodeCharacter).toList();
//                records.removeFirst();
//                records.addFirst(cleanHeader);
//                record = transformRecord(records);
//            }
//            case "ASCII (Fixed)" -> {
//                record = transformRecord(FileParser.fixedWidthFileParser(fileMetaData, 100, "fileMetaData"));
//                System.out.println("ASCII Fixed");
//            }
////            case "Excel" -> {
////                record = FileParser.excelFileParser(fileMetaInfo);
////            }
//        }
//        return inferDataType(connectorName, record);
//    }
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
//    private static String replaceNonPrintableUnicodeCharacter(String input) {
//        Matcher matcher = Pattern.compile("[\\p{C}]").matcher(input);
//        return matcher.replaceAll("");
//    }
//}
