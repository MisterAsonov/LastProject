package com.example.lastproject;

public class User {
    String email, name,lastname, who, referal_link;

    public User(){

    }

    public User(String email, String name, String lastname, String who, String referal_link) {
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.who = who;
        this.referal_link = referal_link;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getReferal_link() {
        return referal_link;
    }

    public void setReferal_link(String referal_link) {
        this.referal_link = referal_link;
    }
}
