package com.architect.process_service.service.job;

import com.architect.process_service.service.job.dto.EventDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class JobHandler {
    private final Executor executor;
    private final JobService jobService;

    JobHandler(@Qualifier("taskExecutor") Executor executor,
               JobService jobService) {
        this.executor = executor;
        this.jobService = jobService;
    }

    public void startAsyncTask(EventDto eventDto) {
        CompletableFuture.runAsync(
                () -> {
                    boolean isExecutable = this.jobService.isJobExecutable(eventDto);
                },
                executor
        );
    }
}
