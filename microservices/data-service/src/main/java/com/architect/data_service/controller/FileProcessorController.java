package com.architect.data_service.controller;

import com.architect.data_service.dto.FileDetailDto;
import com.architect.data_service.dto.FileMetaDataDto;
import com.architect.data_service.file_util.FileProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("process-file")
public class FileProcessorController {
    private final FileProcessor fileProcessor;

    FileProcessorController(@Qualifier("basicFileProcessor") FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }

    @PostMapping
    public void processFile() {
        FileMetaDataDto fileMetaDataDto = FileMetaDataDto.builder()
                .fileType("")
                .filePath("D:\\test.txt")
                .connectorName("ASCII (Delimited)")
                .fieldSeparator("|")
                .fieldDelimiter("None")
                .hasHeader(true)
                .build();
        List<FileDetailDto> fileDetailDtos = fileProcessor.getFileDetails(fileMetaDataDto);
    }
}
