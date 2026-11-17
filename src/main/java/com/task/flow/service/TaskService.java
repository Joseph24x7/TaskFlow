package com.task.flow.service;

import com.task.flow.entity.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    public ResponseEntity<List<Task>> getAllTasks() {
    }

    public ResponseEntity<Task> addNewTask() {
    }

    public ResponseEntity<Task> updateTask(Integer id) {
    }

    public void deleteTask(Integer id) {
    }
}
