package com.architect.process_service.service.workflow.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JobDetailDto {
    private long jobId;
    private long previousJobId;
    private long nextJobId;
    private long eventId;
    private String jobName;
}
