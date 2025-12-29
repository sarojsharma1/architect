package com.architect.process_service.service.workflow.job;

import com.architect.process_service.service.workflow.dto.EventDto;
import com.architect.process_service.service.workflow.dto.JobDetailDto;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    private final JobStrategyFactory jobStrategyFactory;

    public JobService(JobStrategyFactory jobStrategyFactory) {
        this.jobStrategyFactory = jobStrategyFactory;
    }

    public JobDetailDto determineNextJob(EventDto eventDto) {
        //fetch next job or based on file type
        return JobDetailDto.builder().jobId(1).build();
    }

    public boolean isJobExecutable(EventDto eventDto) {
        return true;
    }

    @Async("taskExecutor")
    public void dispatchJob(EventDto eventDto) {
        JobDetailDto jobDetailDto = determineNextJob(EventDto.builder().build());
        boolean isExecutable = isJobExecutable(EventDto.builder().build());
        if (isExecutable) {
            String jobName = jobDetailDto.getJobName();
            jobStrategyFactory.getStrategy(jobName).execute(jobDetailDto);
        } else {
            System.out.println("Job already completed or skipped");
        }
    }
}
