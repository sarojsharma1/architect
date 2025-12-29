package com.architect.process_service.service.workflow.job;

import com.architect.process_service.service.workflow.dto.JobDetailDto;

public class CustomUnzipHandler implements UnzipHandler {
    @Override
    public void unzip(JobDetailDto jobContext) {
        System.out.println("Custom implementation");
    }
}
