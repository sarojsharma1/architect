package com.architect.process_service.service.job.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "JobDetail")
public class JobDetail extends BaseEntity {
    @Column(name = "JobName")
    private String jobName;

    @JoinColumn(name = "workflow_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private WorkflowDetail workflowDetail;
}
