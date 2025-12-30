package com.architect.process_service.service.workflow.job;

import com.architect.process_service.service.workflow.dto.EventDto;
import com.architect.process_service.service.workflow.dto.JobDetailDto;
import com.architect.process_service.service.workflow.enum_obj.JobStatus;
import com.architect.process_service.service.workflow.JobEventPublisher;

import java.time.Instant;

public interface JobStrategy {
    default void beforeExecute(JobDetailDto jobDetailDto) {
        System.out.println(jobDetailDto.getJobName().toLowerCase() + " Job is started!");
        setStatus(JobStatus.INITIATED);
    }

    default void afterExecute(JobDetailDto jobDetailDto, JobEventPublisher jobEventPublisher) {
        // Only emit event if job succeeded
        EventDto eventDto = EventDto.builder()
                .workflowId("1")
                .jobId("1")
                .status(JobStatus.INITIATED)
                .occurredAt(Instant.now())
                .attempt(0)
                .build();
        jobEventPublisher.sendMessage(eventDto);
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
