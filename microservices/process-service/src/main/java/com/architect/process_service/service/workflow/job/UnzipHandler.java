package com.architect.process_service.service.workflow.job;

import com.architect.process_service.service.workflow.dto.JobDetailDto;

public interface UnzipHandler {
    void unzip(JobDetailDto jobContext);
}
