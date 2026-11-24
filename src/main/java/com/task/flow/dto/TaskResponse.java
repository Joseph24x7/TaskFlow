package com.task.flow.dto;

import java.time.LocalDate;

public record TaskResponse(Integer id, String title, String description, LocalDate endDate) {}
