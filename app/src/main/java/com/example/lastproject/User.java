package com.example.lastproject;

public class User {
    String email, name,lastname, code;

    public User(){

    }

    public User(String email, String name, String lastname, String code) {
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
