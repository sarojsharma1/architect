package com.architect.process_service.service.workflow.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JobDetailDto {
    private long jobId;
    private String jobName;
}