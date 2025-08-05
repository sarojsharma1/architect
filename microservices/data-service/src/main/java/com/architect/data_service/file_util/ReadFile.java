package com.architect.data_service.file_util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class ReadFile {
    public static List<List<String>> readSampleFile(String filePath,
                                                    int noOfLines) {
        int offset = 0;
        List<String> fileLines = new ArrayList<>();
        List<List<String>> result = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(filePath));
             BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
        ) {
            lines.filter(line -> !line.trim().isEmpty())
                    .skip(offset)
                    .limit(noOfLines)
                    .forEach(fileLines::add);
            List<String> fileMetaInfo = new ArrayList<>();
            try {
                fileMetaInfo.add(ExtractMetaData.getRecSeparator(inputStream));
            } catch (Exception e) {
                log.error("sorry, unable to extract file details");
            }
            result.add(fileLines);
            result.add(fileMetaInfo);
            return result;
        } catch (NoSuchFileException ex) {
            log.error("Provided source location is invalid or not found", ex);
//            throw new NotFoundException("Provided source location is invalid or not found");
        } catch (AccessDeniedException ex) {
            log.error("Access denied while reading the file", ex);
//            throw new OperationFailedException("Access denied while reading the file", ex.getMessage());
        } catch (IOException ex) {
            log.error("Error occurred while reading the file", ex);
//            throw new OperationFailedException("Error occurred while reading the file", ex.getMessage());
        }
    }
}
