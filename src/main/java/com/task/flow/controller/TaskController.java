package com.task.flow.controller;

import com.task.flow.dto.BulkRequest;
import com.task.flow.entity.Task;
import com.task.flow.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Integer id) {
        return taskService.getTaskById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Task> addNewTask(@RequestBody Task task) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.addNewTask(task));
    }

    @PostMapping("/bulk")
    private ResponseEntity<Integer> addBulkTask(@RequestBody BulkRequest bulkRequest) {

        long startTime = System.currentTimeMillis();

        Integer tasksProcessed = taskService.addBulkTask(bulkRequest.task(), bulkRequest.count());

        log.info("Total time for {} of task: {}", bulkRequest.count(), System.currentTimeMillis() - startTime);

        return ResponseEntity.status(HttpStatus.CREATED).body(tasksProcessed);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Integer id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
