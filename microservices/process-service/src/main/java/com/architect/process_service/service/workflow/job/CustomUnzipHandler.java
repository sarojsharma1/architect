package com.architect.process_service.service.workflow.job;

import com.architect.process_service.service.workflow.dto.JobDetailDto;
import org.springframework.stereotype.Service;

@Service("customUnzipHandler")
public class CustomUnzipHandler implements UnzipHandler {
    @Override
    public void unzip(JobDetailDto jobContext) {
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Custom implementation");
    }
}
