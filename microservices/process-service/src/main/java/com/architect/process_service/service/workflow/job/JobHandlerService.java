package com.architect.process_service.service.workflow.job;

import com.architect.process_service.service.workflow.dto.EventDto;
import com.architect.process_service.service.workflow.dto.JobDetailDto;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class JobHandlerService {
    private final JobStrategyFactory jobStrategyFactory;

    public JobHandlerService(JobStrategyFactory jobStrategyFactory) {
        this.jobStrategyFactory = jobStrategyFactory;
    }

    public JobDetailDto determineNextJob(EventDto eventDto) {
        //based on workflow type
        //file type: .zip → UnzipJob
        //Stepwise: JobA → JobB → JobC
        return JobDetailDto.builder().jobId(1).build();
    }

    public boolean isJobExecutable(JobDetailDto jobDetailDto) {
        //check DB for already completed jobs to avoid re-execution
        return true;
    }

    @Async("taskExecutor")
    public void dispatchJob(EventDto eventDto) {
        JobDetailDto jobDetailDto = determineNextJob(EventDto.builder().build());
        jobDetailDto.setJobName("UNZIP");
        boolean isExecutable = isJobExecutable(jobDetailDto);
        if (isExecutable) {
            String jobName = jobDetailDto.getJobName();
            jobStrategyFactory.getStrategy(jobName).execute(jobDetailDto);
        } else {
            System.out.println("Job already completed or skipped");
        }
    }
}
