package com.task.flow.service;

import com.task.flow.entity.Task;
import com.task.flow.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task addNewTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Integer id, Task task) {

        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("ID not found"));

        existingTask.updateTask(task);

        taskRepository.save(existingTask);

        return existingTask;
    }

    public void deleteTask(Integer id) {

        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("ID not found"));

        taskRepository.delete(existingTask);

    }

    public Task getTaskById(Integer id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("ID not found"));
    }
}
