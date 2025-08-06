package com.architect.data_service.file_util;

import com.architect.common_lib.exception.OperationFailedException;
import com.architect.data_service.dto.FileMetaDataDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FileParser {
    public static List<List<String>> delimitedFileParser(FileMetaDataDto fileMetaData,
                                                         int noOfLinesToProcess
    ) {
        CSVFormat.Builder csvFormat = CSVFormat.DEFAULT.builder()
                .setTrim(true);
        if (fileMetaData.getHasHeader()) csvFormat.setHeader();
        csvFormat.setRecordSeparator(System.lineSeparator());
        csvFormat.setDelimiter(fileMetaData.getFieldSeparator());

        if (fileMetaData.getFieldDelimiter() != null && !fileMetaData.getFieldDelimiter().equals("None")) {
            csvFormat.setQuote(fileMetaData.getFieldDelimiter().charAt(0));
        } else {
            csvFormat.setQuote(null);
        }
        String fileLines = ReadFile.readSampleFile(fileMetaData.getFilePath(), noOfLinesToProcess)
                .getFirst().stream()
                .collect(Collectors.joining(System.lineSeparator()));
        try {
            List<String> headers;
            List<List<String>> rows;
            try (CSVParser csvParser = CSVParser.parse(fileLines, csvFormat.get())) {
                headers = new ArrayList<>(csvParser.getHeaderNames());
                rows = csvParser.getRecords()
                        .stream()
                        .map(record -> Arrays.asList(record.values()))
                        .collect(Collectors.toList());
            }

            if (headers.isEmpty()) {
                int columnCount = rows.getFirst().size();
                for (int i = 1; i <= columnCount; i++) {
                    headers.add("Field" + i);
                }
            }
            rows.addFirst(headers);
            return rows;
        } catch (IllegalArgumentException e) {
            log.error("Field separator and field delimiter cannot be the same", e);
            throw new OperationFailedException("Field separator and field delimiter cannot be the same");
        } catch (IOException e) {
            log.error("Couldn't parse file content. Please ensure field separator is correct.", e);
            throw new OperationFailedException("Couldn't parse file content. Please ensure field separator is correct.");
        } catch (Exception e) {
            log.error("Unexpected error occurred. Please ensure the input is correct and try again.", e);
            throw new OperationFailedException("Unexpected error occurred. Please ensure the input is correct and try again.");
        }
    }
}
