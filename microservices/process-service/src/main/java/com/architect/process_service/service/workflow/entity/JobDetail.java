package com.architect.process_service.service.workflow.entity;

import com.architect.process_service.service.workflow.enum_obj.JobStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job_detail")
public class JobDetail extends BaseEntity {
    @Column(name = "job_name")
    private String jobName;

    @Column(name = "step_order")
    private Integer stepOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JobStatus status;

    @ManyToOne()
    @JoinColumn(name = "workflow_detail_id")
    private WorkflowDetail workflowDetail;
}
