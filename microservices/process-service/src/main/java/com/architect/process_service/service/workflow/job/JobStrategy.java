package com.architect.process_service.service.workflow.job;

import com.architect.process_service.service.workflow.dto.JobDetailDto;
import com.architect.process_service.service.workflow.enum_obj.JobStatus;

public interface JobStrategy {
    default void beforeExecute(JobDetailDto jobDetailDto) {
        setStatus(JobStatus.INITIATED);
    }

    default void afterExecute(JobDetailDto jobDetailDto) {
        // Only emit event if job succeeded
    }

    default void onFailure(JobDetailDto jobDetailDto, Exception e) {
    }

    default boolean isSkippable() {
        return false;
    }

    JobStatus getStatus();

    void setStatus(JobStatus status);

    void execute(JobDetailDto jobDetailDto);
}
