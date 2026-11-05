package com.architect.process_service.service.workflow.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class EventDto {
    private final String eventId = UUID.randomUUID().toString();
    private String workflowId;
    private String jobId;
}