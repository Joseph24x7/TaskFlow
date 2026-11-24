package com.task.flow.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Integer id) {
        super("Task with ID " + id + " was not found");
    }
}
