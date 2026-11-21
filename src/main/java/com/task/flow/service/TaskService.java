package com.task.flow.service;

import com.task.flow.entity.Task;
import com.task.flow.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public int addBulkTask(Task task, int count) {

        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {

            List<Future<?>> futures = new ArrayList<>();

            for (int i = 0; i < count; i++) {

                futures.add(executorService.submit(() -> {

                    Task newTask = new Task();

                    newTask.setDescription(task.getDescription());
                    newTask.setTitle(task.getTitle());
                    newTask.setEndDate(task.getEndDate());

                    taskRepository.save(newTask);
                }));
            }

            for (Future<?> future : futures) {
                future.get();
            }

            return futures.size();

        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
