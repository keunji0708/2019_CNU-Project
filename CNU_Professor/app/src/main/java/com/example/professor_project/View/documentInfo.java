package com.example.professor_project.View;

public class documentInfo {
    private String day;
    private String id;
    private String startAt;
    private String endAt;

    public documentInfo(String id, String day, String startAt, String endAt){
        this.id = id;
        this.day = day;
        this.startAt = startAt;
        this.endAt = endAt;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartAt() { return startAt; }

    public void setStartAt(String startAt) { this.startAt = startAt; }

    public String getEndAt() { return endAt; }

    public void setEndAt(String endAt) { this.endAt = endAt; }

}
