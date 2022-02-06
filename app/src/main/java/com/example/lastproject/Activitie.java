package com.example.lastproject;

public class Activitie {
    String name, act_desc, type, date, when, creator_id;

    public Activitie(String name, String act_desc, String type, String date, String when, String creator_id) {
        this.name = name;
        this.act_desc = act_desc;
        this.type = type;
        this.date = date;
        this.when = when;
        this.creator_id = creator_id;
    }

    public Activitie() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAct_desc() {
        return act_desc;
    }

    public void setAct_desc(String act_desc) {
        this.act_desc = act_desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }
}
