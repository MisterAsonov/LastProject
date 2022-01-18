package com.example.lastproject;

public class Report {
    private String title;
    private String explanation;
    private String status;
    private String date;
    private String room;
    private String building;
    private String creator_id;


    public Report() {
    }

    public Report(String title, String explanation, String status, String date, String room, String building, String creator_id) {
        this.title = title;
        this.explanation = explanation;
        this.status = status;
        this.date = date;
        this.room = room;
        this.building = building;
        this.creator_id = creator_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }
}
