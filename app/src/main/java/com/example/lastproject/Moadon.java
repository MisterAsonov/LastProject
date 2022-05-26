package com.example.lastproject;

public class Moadon {
    int hours, minute, day, month, year;

    public Moadon() {
    }

    @Override
    public String toString() {
        return "Moadon{" +
                "hours=" + hours +
                ", minute=" + minute +
                '}';
    }

    public Moadon(int hours, int minute, int day, int month, int year) {
        this.hours = hours;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.year = year;
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
