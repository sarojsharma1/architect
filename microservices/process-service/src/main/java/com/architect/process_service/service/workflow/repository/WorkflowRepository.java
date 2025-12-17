package com.architect.process_service.service.workflow.repository;

import com.architect.process_service.service.workflow.entity.WorkflowDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowRepository extends JpaRepository<WorkflowDetail, Long> {
}
