package com.architect.process_service.service.workflow.job;

import com.architect.process_service.service.workflow.dto.JobDetailDto;
import com.architect.process_service.service.workflow.enum_obj.JobStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("UNZIP")
public class UnzipJobStrategy implements JobStrategy {
    private final UnzipHandler unzipHandler;

    public UnzipJobStrategy(@Qualifier("customUnzipHandler") UnzipHandler unzipHandler) {
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
