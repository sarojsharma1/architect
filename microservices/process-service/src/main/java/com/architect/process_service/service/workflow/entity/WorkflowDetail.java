package com.architect.process_service.service.workflow.entity;

import com.architect.process_service.service.workflow.enum_obj.WorkflowStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "workflow_detail")
public class WorkflowDetail extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WorkflowStatus status;

    @OneToMany(mappedBy = "workflowDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<JobDetail> jobDetail;
}
