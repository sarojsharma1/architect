package com.architect.process_service.service.job;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class RunJob {
    @Async("executor")
    public void dispatchJob(String job) {
        System.out.println("Inside dispatch job" + job);
    }
}
