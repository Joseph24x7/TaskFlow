package com.task.flow.dto;

import com.task.flow.entity.Task;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record BulkRequest(
        @Min(1) @Max(10000) Integer count,
        @Valid Task task
) {}
