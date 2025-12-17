package com.architect.process_service.service.workflow.repository;

import com.architect.process_service.service.workflow.entity.JobDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDetailRepository extends JpaRepository<JobDetail, Long> {
}
