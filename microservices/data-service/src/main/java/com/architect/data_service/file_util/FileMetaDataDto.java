package com.architect.data_service.file_util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileMetaDataDto {
    private Boolean hasHeader;
    private String connectorName;
    private String fieldSeparator;
    private String fieldDelimiter;
    private String rowTerminator;
    private String filePath;
    private String fileType;
    private String encoding;
}

