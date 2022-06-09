package com.example.lastproject;

import android.media.Image;
import android.net.Uri;

import androidx.constraintlayout.utils.widget.ImageFilterView;

import java.util.ArrayList;


/**
 * מחלקה זו ממשת את הטיול
 */
public class Activitie {

    String event_title, event_location, event_date, event_time, event_desc;
    String imageUrl;
    ArrayList<String> event_participants;

    public Activitie(String event_title, String event_location, String event_date, String event_time, String event_desc, String imageUrl, ArrayList<String> event_participants) {
        this.event_title = event_title;
        this.event_location = event_location;
        this.event_date = event_date;
        this.event_time = event_time;
        this.event_desc = event_desc;
        this.imageUrl = imageUrl;
        this.event_participants = event_participants;
    }

    public Activitie() {}

    /**
     * מחלקה מחזירה שם הטיול
     * @return
     */
    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    /**
     * מחלקה מחזירה מיקום הטיול
     * @return
     */
    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }
    /**
     * מחלקה מחזירה תאריך הטיול
     * @return
     */
    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }
    /**
     * מחלקה מחזירה זמן הטיול
     * @return
     */
    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }
    /**
     * מחלקה מחזירה תיאור הטיול
     * @return
     */
    public String getEvent_desc() {
        return event_desc;
    }

    public void setEvent_desc(String event_desc) {
        this.event_desc = event_desc;
    }
    /**
     * מחלקה מחזירה אוסף משתמשים בטיול
     * @return
     */
    public ArrayList<String> getEvent_participants() {
        return event_participants;
    }

    public void setEvent_participants(ArrayList<String> event_participants) {
        this.event_participants = event_participants;
    }
    /**
     * מחלקה מחזירה קישור לתמונת הטיול
     * @return
     */
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

