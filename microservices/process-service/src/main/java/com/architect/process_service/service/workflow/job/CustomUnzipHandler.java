package com.architect.process_service.service.workflow.job;

import com.architect.process_service.service.workflow.dto.JobDetailDto;
import org.springframework.stereotype.Service;

@Service
public class CustomUnzipHandler implements UnzipHandler {
    @Override
    public void unzip(JobDetailDto jobContext) {
        System.out.println("Custom implementation");
    }
}
