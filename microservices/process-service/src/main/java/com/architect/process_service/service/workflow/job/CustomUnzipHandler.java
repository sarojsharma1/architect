package com.architect.process_service.service.workflow.job;

import com.architect.process_service.service.workflow.dto.JobDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("customUnzipHandler")
@Slf4j
public class CustomUnzipHandler implements UnzipHandler {
    @Override
    public void unzip(JobDetailDto jobContext) {
        try {
            log.info(Thread.currentThread().getName());
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Custom implementation");
    }
}
