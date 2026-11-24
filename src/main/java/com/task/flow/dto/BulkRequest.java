package com.task.flow.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record BulkRequest(
        @NotNull
        @Size(max = 1000, message = "Maximum 1000 tasks are allowed")
        @Valid
        List<TaskRequest> tasks
) {}