package com.architect.process_service.service.workflow.job;

import com.architect.process_service.service.workflow.dto.JobDetailDto;
import com.architect.process_service.service.workflow.enum_obj.JobStatus;
import org.springframework.stereotype.Service;

@Service
public class UnzipJobStrategy implements JobStrategy {
    private final UnzipHandler unzipHandler;

    public UnzipJobStrategy(UnzipHandler unzipHandler) {
        this.unzipHandler = unzipHandler;
    }

    @Override
    public JobStatus getStatus() {
        return null;
    }

    @Override
    public void setStatus(JobStatus status) {
        //set event and update to database
    }

    @Override
    public void execute(JobDetailDto jobContext) {
        unzipHandler.unzip(jobContext);
        setStatus(JobStatus.COMPLETED);
    }
}
