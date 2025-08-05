package com.architect.data_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDetailDto {
    private Integer sourceColumnNo;
    private String sourceColumnName;
    private String sourceDataType;
    private Integer sourceDataLength;
    private Integer targetColumnNo;
    private String targetColumnName;
    private String targetDataType;
    private Integer targetDataLength;
    private Integer targetDataPrecision;
    private Integer targetDecimalPlaces;
    private Boolean targetNullable;
    private String transformationRule;
}

