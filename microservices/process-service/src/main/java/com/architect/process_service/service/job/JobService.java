package com.architect.process_service.service.job;

import com.architect.process_service.service.job.dto.EventDto;
import com.architect.process_service.service.job.dto.JobDto;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    //repository

    public boolean isJobExecutable(EventDto eventDto) {
        return true;
    }

    public JobDto getNextJob(EventDto eventDto) {
        //use mapper to change jobDetailEntity to JobDetailDto
        return JobDto.builder().jobId(1).build();
    }
}
