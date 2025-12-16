package com.architect.process_service.service.job.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "WorkflowDetail")
public class WorkflowDetail extends BaseEntity {
    @OneToMany(mappedBy = "workflowDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<JobDetail> jobDetail;
}
