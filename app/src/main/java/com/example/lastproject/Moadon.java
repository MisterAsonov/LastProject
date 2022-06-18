package com.example.lastproject;

public class Moadon {
    int hours, minute, day, month, year;

    /**
     * מחלקה שממשת מה זה שיחת קבוצה
     */
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

    /**
     * מחלקה שמחזירה את השעה של שיחת קבוצה
     * @return
     */
    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
    /**
     * מחלקה שמחזירה את הדקה של שיחת קבוצה
     * @return
     */
    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
    /**
     * מחלקה שמחזירה את היום של שיחת קבוצה
     * @return
     */
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
    /**
     * מחלקה שמחזירה את החודש של שיחת קבוצה
     * @return
     */
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
    /**
     * מחלקה שמחזירה את השנה של שיחת קבוצה
     * @return
     */
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}