package com.architect.process_service.service.workflow.dto;

import com.architect.process_service.service.workflow.enum_obj.JobStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class JobDetailDto {
    private Long jobId;
    private String jobName;
    private Integer stepOrder;
    private Map<String, Object> input;
    private Boolean skipAble;
    private Integer retryAttempt;
    private JobStatus status;
}
