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
        //(eventDto.getWorkflowId(), eventDto.getJobId()) ---> todo
        //at first the jobId will be null initially
        //  Custom-ordered workflow (DB-driven):
        //    if workflow.orderType == CUSTOM
        //      - Fetch the next job from the database based on: workflowId and lastCompletedJob
        //      - Example: JobA -> JobB -> JobC

        // FileType-ordered workflow (fallback):
        //    if no custom workflow exists or orderType != CUSTOM
        //      - Determine job based on file extension
        //      - Example: .zip -> UNZIP, .pgp -> DECRYPT

        return JobDetailDto.builder()
                .jobId(1)
                .jobName("UNZIP")
                .build();
    }

    public boolean isJobExecutable(JobDetailDto jobDetailDto) {
        //check DB for already completed jobs to avoid re-execution
        return true;
    }

    @Async("taskExecutor")
    public void dispatchJob(EventDto inEvent) {
//        JobDetailDto jobDetailDto = jobMapper.toJobDetail(inEvent);
        JobDetailDto jobDetailDto = determineNextJob(inEvent);
        if (isJobExecutable(jobDetailDto)) {
            String jobName = jobDetailDto.getJobName();
            jobStrategyFactory.getStrategy(jobName).execute(jobDetailDto);
        } else {
            System.out.println("Job already completed or skipped");
        }
    }
}
