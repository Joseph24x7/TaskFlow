package com.task.flow.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Task {

    private Integer id;
    private String title;
    private String description;
    private Date endDate;

}
