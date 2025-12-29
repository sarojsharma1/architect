package com.architect.process_service.service.workflow.dto;

import com.architect.process_service.service.workflow.enum_obj.JobStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Builder
@Data
public class EventDto {
    private final String eventId = UUID.randomUUID().toString();
    private String workflowId;                                      // Identifies the workflow instance
    private String jobId;                                           // JobStrategy that just finished
    private JobStatus status;                                       // COMPLETED / FAILED
    private Instant occurredAt;                                     // EventDto time
    private int attempt;                                            // Retry attempt (optional)
    private JobDetailDto jobDetailDto;
}