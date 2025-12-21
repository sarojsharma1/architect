package com.architect.process_service.service.workflow.job;

import org.springframework.stereotype.Service;

@Service
public class UnzipJobHandler {
    public String unzip(String input) {
        return "Output";
    }

    public boolean canHandle(String fileName) {
        return fileName.endsWith(".zip");
    }
}
