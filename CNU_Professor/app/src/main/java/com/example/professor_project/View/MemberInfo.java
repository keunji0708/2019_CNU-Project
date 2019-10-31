package com.example.professor_project.View;

public class MemberInfo {
    private String name;
    private String phoneNum;
    private String room;
    private String major;

    public MemberInfo(String name, String phoneNumber, String room, String major){
        this.name = name;
        this.phoneNum = phoneNumber;
        this.room = room;
        this. major = major;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
