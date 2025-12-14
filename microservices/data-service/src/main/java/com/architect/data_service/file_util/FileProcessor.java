package com.architect.data_service.file_util;

import com.architect.data_service.dto.FileDetailDto;
import com.architect.data_service.dto.FileMetaDataDto;

import java.util.List;
import java.util.Map;

public interface FileProcessor {
    public List<FileDetailDto> getFileDetails(FileMetaDataDto fileMetaData);

    public Map<String, List<String>> transformRecord(List<List<String>> rows);
}
