package com.task.flow.controller;

import com.task.flow.dto.BulkRequest;
import com.task.flow.dto.TaskRequest;
import com.task.flow.dto.TaskResponse;
import com.task.flow.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Integer id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> addNewTask(@Valid @RequestBody TaskRequest taskRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.addNewTask(taskRequest));
    }

    @PostMapping("/bulk")
    public ResponseEntity<Integer> addBulkTask(@Valid @RequestBody BulkRequest bulkRequest) {

        long startTime = System.nanoTime();

        int tasksProcessed = taskService.addBulkTask(bulkRequest.task(), bulkRequest.count());

        long elapsedMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
        log.info("Created {}/{} tasks in {} ms", tasksProcessed, bulkRequest.count(), elapsedMillis);

        return ResponseEntity.status(HttpStatus.CREATED).body(tasksProcessed);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Integer id, @Valid @RequestBody TaskRequest taskRequest) {
        return taskService.updateTask(id, taskRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
