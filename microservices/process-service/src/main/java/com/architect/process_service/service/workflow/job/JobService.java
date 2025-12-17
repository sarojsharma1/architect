package com.architect.process_service.service.workflow.job;

import com.architect.process_service.service.workflow.dto.EventDto;
import com.architect.process_service.service.workflow.dto.JobDetailDto;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    private final UnzipJobHandler unzipJobHandler;

    public JobService(UnzipJobHandler unzipJobHandler) {
        this.unzipJobHandler = unzipJobHandler;
    }
    //repository

    public boolean isJobExecutable(EventDto eventDto) {
        return true;
    }

    public JobDetailDto getNextJob(EventDto eventDto) {
        //use mapper to change jobDetailEntity to JobDetailDto
        return JobDetailDto.builder().jobId(1).build();
    }

    @Async("taskExecutor")
    public void dispatchJob(String job) {
        isJobExecutable(EventDto.builder().build());
        JobDetailDto jobDetailDto = getNextJob(EventDto.builder().build());
        String jobName = jobDetailDto.getJobName();
        this.unzipJobHandler.unzip();
    }
}
