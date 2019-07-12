package com.example.checktask.data;

import java.util.Date;

public class Task {
    private Integer id;
    private String detail;
    private String date;
    private String completed;

    public Task(Integer id, String detail, String date, String completed) {
        this.id = id;
        this.detail = detail;
        this.date = date;
        this.completed = completed;
    }

    public Integer getId() {
        return id;
    }

    public String getDetail() {
        return detail;
    }

    public String getDate() {
        return date;
    }

    public String getCompleted() {
        return completed;
    }

}
