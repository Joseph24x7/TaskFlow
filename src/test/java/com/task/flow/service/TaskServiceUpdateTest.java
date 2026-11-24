package com.task.flow.service;

import com.task.flow.dto.TaskRequest;
import com.task.flow.entity.Task;
import com.task.flow.exception.TaskNotFoundException;
import com.task.flow.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:task-update-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class TaskServiceUpdateTest {

    @Autowired
    private TaskService taskService;

    @Test
    void deletingMissingTaskThrowsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(-1));
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
