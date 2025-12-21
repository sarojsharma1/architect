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

    public boolean hasNextJob() {
        return true;

        //wrap the whole jobs into a workflow
        // Start ------ previous_job_id ------- current_job_id -------- next_job_id ------- End

        // start ko case ma previous_job_id = null
        // end ko case ma next_job_id = null
    }

    @Async("taskExecutor")
    public void dispatchJob(String job) {
        boolean nextJob = hasNextJob();
        JobDetailDto jobDetailDto = getNextJob(EventDto.builder().build());
        isJobExecutable(EventDto.builder().build());
        String jobName = jobDetailDto.getJobName();
        String name = this.unzipJobHandler.unzip("input");
    }
}
