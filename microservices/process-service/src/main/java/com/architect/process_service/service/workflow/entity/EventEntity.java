package com.architect.process_service.service.workflow.entity;

import com.architect.process_service.service.workflow.enum_obj.JobStatus;
import jakarta.persistence.Entity;

import java.time.Instant;
import java.util.UUID;

@Entity
public class EventEntity extends BaseEntity {
    private final String eventId = UUID.randomUUID().toString();
    private String workflowId;
    private String jobId;
    private JobStatus status;
    private Instant occurredAt;
    private int attempt;
}
