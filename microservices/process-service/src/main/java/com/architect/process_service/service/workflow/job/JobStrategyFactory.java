package com.architect.process_service.service.workflow.job;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JobStrategyFactory {
    private final Map<String, JobStrategy> strategyMap;

    public JobStrategyFactory(Map<String, JobStrategy> strategyMap) {
        this.strategyMap = strategyMap;
    }

    public JobStrategy getStrategy(String jobName) {
        return strategyMap.get(jobName);
    }
}
