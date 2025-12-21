package com.architect.process_service.service.workflow.job;

import com.architect.process_service.service.workflow.dto.EventDto;
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
        CompletableFuture.supplyAsync(
                () -> {
                    boolean isExecutable = this.jobService.isJobExecutable(eventDto);
                    this.jobService.dispatchJob("test");
                    return 5;
                },
                executor
        ).thenApply((a) -> {
            return "test";
        }).thenAccept(
                System.out::println
        );
    }
}
