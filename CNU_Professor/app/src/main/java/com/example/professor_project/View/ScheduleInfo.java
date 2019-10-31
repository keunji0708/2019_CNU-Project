package com.example.professor_project.View;

import java.util.Date;

public class ScheduleInfo {
    private String title;
    private String state;
    private String day;
    private String publisher;
//    private Date startAt;
//    private Date endAt;
    private String startAt;
    private String endAt;
    private String id;

    public ScheduleInfo(String title, String state, String day, String publisher,
                        String startAt, String endAt) {
        this.title = title;
        this.state = state;
        this.day = day;
        this.publisher = publisher;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public ScheduleInfo(String title, String state, String day, String publisher,
                        String startAt, String endAt, String id) {
        this.title = title;
        this.state = state;
        this.day = day;
        this.publisher = publisher;
        this.startAt = startAt;
        this.endAt = endAt;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
