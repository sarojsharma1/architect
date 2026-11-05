package com.architect.process_service.service.workflow.dto;

import com.architect.process_service.service.workflow.enum_obj.WorkflowStatus;
import com.architect.process_service.service.workflow.enum_obj.WorkflowType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class WorkflowDto {
    private Long workflowId;
    private WorkflowType workflowType;
    private List<JobDetailDto> jobDetailDto;
    private WorkflowStatus status;
}
