package com.architect.process_service.service.workflow.job;

public interface FileTypeStrategy {
    boolean match();

    String jobName();
}
