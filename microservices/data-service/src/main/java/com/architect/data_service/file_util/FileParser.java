//package com.architect.data_service.file_util;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVParser;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//public class FileParser {
//    public static List<List<String>> delimitedFileParser(FileMetaDataDto fileMetaData,
//                                                         int noOfLinesToProcess,
//                                                         String operation
//    ) {
//        CSVFormat.Builder csvFormat = CSVFormat.DEFAULT.builder()
//                .setTrim(true);
//        if (fileMetaData.getHasHeader()) csvFormat.setHeader();
//        csvFormat.setRecordSeparator(System.lineSeparator());
//
//        if (fileMetaData.getFieldSeparator() == null || fileMetaData.getFieldSeparator().isEmpty()) {
//            if (operation.equals("filePreview")) {
//                return null;
////                return rawFilePreview(fileMetaData.getFilePath(), noOfLinesToProcess);
//            } else {
//                log.error("Field separator cannot be empty while populating field metadata.");
////                throw new BadRequestException("Field separator cannot be empty while populating field metadata.");
//            }
//        } else {
//            csvFormat.setDelimiter(fileMetaData.getFieldSeparator());
//        }
//        if (fileMetaData.getFieldDelimiter() != null && !fileMetaData.getFieldDelimiter().equals("None")) {
//            csvFormat.setQuote(fileMetaData.getFieldDelimiter().charAt(0));
//        } else {
//            csvFormat.setQuote(null);
//        }
//        String fileLines = ReadFile.readSampleFile(fileMetaData.getFilePath(), noOfLinesToProcess)
//                .getFirst().stream()
//                .collect(Collectors.joining(System.lineSeparator()));
//        try {
//            CSVParser csvParser = CSVParser.parse(fileLines, csvFormat.get());
//            List<String> headers = new ArrayList<>(csvParser.getHeaderNames());
//            List<List<String>> rows = csvParser.getRecords()
//                    .stream()
//                    .map(record -> Arrays.asList(record.values()))
//                    .collect(Collectors.toList());
//
//            if (headers.isEmpty()) {
//                int columnCount = rows.getFirst().size();
//                for (int i = 1; i <= columnCount; i++) {
//                    headers.add("Field" + i);
//                }
//            }
//            rows.addFirst(headers);
//            return rows;
//        } catch (IllegalArgumentException e) {
//            log.error("Field separator and field delimiter cannot be the same", e);
////            throw new OperationFailedException("Field separator and field delimiter cannot be the same");
//        } catch (IOException e) {
//            log.error("Couldn't parse file content. Please ensure field separator is correct.", e);
////            throw new OperationFailedException("Couldn't parse file content. Please ensure field separator is correct.");
//        } catch (Exception e) {
//            log.error("Unexpected error occurred. Please ensure the input is correct and try again.", e);
////            throw new OperationFailedException("Unexpected error occurred. Please ensure the input is correct and try again.");
//        }
//    }
//
//    public static List<List<String>> fixedWidthFileParser(FileMetaDataDto fileMetaData,
//                                                          int noOfLinesToProcess,
//                                                          String operation) {
//        //TODO:
//        return null;
//    }
//
//
//    public static List<List<String>> excelFileParser(FileMetaDataDto fileMetaData,
//                                                     int noOfLinesToProcess,
//                                                     String operation) {
//        //TODO:
//        return null;
//    }
//}
