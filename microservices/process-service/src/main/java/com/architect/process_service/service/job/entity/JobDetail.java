package com.architect.process_service.service.job.entity;

import jakarta.persistence.*;

@Entity
public class JobDetail extends BaseEntity {

    @Column(name = "JobName")
    private String jobName;

    @JoinColumn(name = "workflow_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private WorkflowDetail workflowDetail;
}
