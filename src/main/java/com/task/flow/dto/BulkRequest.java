package com.task.flow.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BulkRequest(
        @NotNull @Min(1) @Max(10000) Integer count,
        @NotNull @Valid TaskRequest task
) {}
