package com.architect.process_service.service.workflow.job;

import org.springframework.stereotype.Service;

@Service
public class JobStateService {

    public void onInitiated() {
    }

    public void onCompleted() {
//        nextJob = fetchNextJob(event.previousJobId)
//        canExecute = isJobExecutable(nextJob)			//Check if the next job is eligible to run (idempotency check)
//
//        if canExecute:
//
//        executeJob(nextJob)							// Execute the next job
//
//        updateJobStatus(nextJob.id, "COMPLETED")	// After successful execution, update job status in DB
//
//        triggerJobCompletedEvent(nextJob.id)		// Trigger event for the next job in the sequence


    }

    public void onFailed() {
//        failedJob = fetchJob(event.jobId)
//
//        if failedJob.retryCount < MAX_RETRIES:
//
//        incrementRetryCount(failedJob.id)
//
//        retryJob(failedJob)
//
//
//	else:
//        updateJobStatus(failedJob.id, "FAILED")
//
//        sendToDeadLetterQueue(failedJob)

    }
}
