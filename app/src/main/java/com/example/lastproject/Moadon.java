package com.example.lastproject;

public class Moadon {
    int hours, minute;

    public Moadon() {
    }

    @Override
    public String toString() {
        return "Moadon{" +
                "hours=" + hours +
                ", minute=" + minute +
                '}';
    }

    public Moadon(int hours, int minute) {
        this.hours = hours;
        this.minute = minute;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
