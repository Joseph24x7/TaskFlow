package com.task.flow.service;

import com.task.flow.dto.TaskRequest;
import com.task.flow.dto.TaskResponse;
import com.task.flow.entity.Task;
import com.task.flow.exception.TaskNotFoundException;
import com.task.flow.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskService::toResponse)
                .toList();
    }

    @Transactional
    public TaskResponse addNewTask(TaskRequest taskRequest) {
        return toResponse(taskRepository.save(toEntity(taskRequest)));
    }

    @Transactional
    public TaskResponse updateTask(Integer id, TaskRequest taskRequest) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        Task updatedTask = updateEntity(existingTask, taskRequest);

        taskRepository.save(updatedTask);

        return toResponse(updatedTask);
    }

    private Task updateEntity(Task existingTask, TaskRequest taskRequest) {
        existingTask.setTitle(taskRequest.title());
        existingTask.setDescription(taskRequest.description());
        existingTask.setEndDate(taskRequest.endDate());
        return existingTask;
    }

    public void deleteTask(Integer id) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(existingTask);
    }

    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Integer id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        return toResponse(task);
    }

    @Transactional
    public int addBulkTask(List<TaskRequest> taskRequests) {
        List<Task> tasks = toEntity(taskRequests);
        return taskRepository.saveAll(tasks).size();
    }

    private static Task toEntity(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.title());
        task.setDescription(taskRequest.description());
        task.setEndDate(taskRequest.endDate());
        return task;
    }

    private static List<Task> toEntity(List<TaskRequest> taskRequests) {

        List<Task> tasks = new ArrayList<>(taskRequests.size());

        taskRequests.forEach(taskRequest -> {
            Task task = new Task();
            task.setTitle(taskRequest.title());
            task.setDescription(taskRequest.description());
            task.setEndDate(taskRequest.endDate());
            tasks.add(task);
        });

        return tasks;
    }

    private static TaskResponse toResponse(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getEndDate());
    }
}
