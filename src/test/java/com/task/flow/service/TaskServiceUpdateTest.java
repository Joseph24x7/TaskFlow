package com.task.flow.service;

import com.task.flow.dto.TaskRequest;
import com.task.flow.entity.Task;
import com.task.flow.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskServiceUpdateTest {

    @Test
    void deleteTaskRunsInTransaction() throws NoSuchMethodException {
        assertNotNull(TaskService.class.getMethod("deleteTask", Integer.class)
                .getAnnotation(Transactional.class));
    }

    @Test
    void updateReplacesAllTaskFields() {
        TaskRepository taskRepository = mock(TaskRepository.class);
        Task existingTask = new Task();
        existingTask.setId(1);
        existingTask.setTitle("Old title");
        existingTask.setDescription("Old description");
        existingTask.setEndDate(LocalDate.of(2026, 1, 1));
        when(taskRepository.findById(1)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        TaskService taskService = new TaskService(taskRepository);

        var updatedTask = taskService.updateTask(1, new TaskRequest("", "", null));

        assertEquals("", updatedTask.title());
        assertEquals("", updatedTask.description());
        assertNull(updatedTask.endDate());
    }
}
