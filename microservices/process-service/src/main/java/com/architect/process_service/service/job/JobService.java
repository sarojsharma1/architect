package com.architect.process_service.service.job;

import org.springframework.stereotype.Service;

@Service
public class JobService {
    public void getJobById(String jobId)           //pass job id(event.jobId) return a job detail
    {
    }

    public void isJobExecutable(String jobId) {   //pass job id(event.jobId) to check idempotency
    }
}
