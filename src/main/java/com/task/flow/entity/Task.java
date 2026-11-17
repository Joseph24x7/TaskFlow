package com.task.flow.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private Date endDate;

    public void updateTask(Task task) {
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.endDate = task.getEndDate();
    }

}
