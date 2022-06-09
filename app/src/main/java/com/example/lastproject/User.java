package com.example.lastproject;

public class User {
    String email, name, lastname, who, referal_link, UID, mImageUrl;

    public User() {

    }

    /**
     * חלקה מגדירה מח זה משתמש
     * @param email
     * @param name
     * @param lastname
     * @param who
     * @param referal_link
     * @param UID
     * @param mImageUrl
     */
    public User(String email, String name, String lastname, String who, String referal_link, String UID, String mImageUrl) {
        this.mImageUrl = mImageUrl;
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.who = who;
        this.referal_link = referal_link;
        this.UID = UID;

    }

    /**
     * מחלקה מחזירה את הדואר של משתמש
     * @return
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * מחלקה מחזירה את השם של משתמש
     * @return
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /**
     * מחלקה מחזירה את השם המשפחה של משתמש
     * @return
     */
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    /**
     * מחלקה מחזירה את הסוג של משתמש
     * @return
     */
    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
    /**
     * מחלקה מחזירה את קוד הזמנה של משתמש
     * @return
     */
    public String getReferal_link() {
        return referal_link;
    }

    public void setReferal_link(String referal_link) {
        this.referal_link = referal_link;
    }
    /**
     * מחלקה מחזירה את הId של משתמש
     * @return
     */
    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
    /**
     * מחלקה מחזירה את הקישור לתמונת פרופיל של משתמש
     * @return
     */
    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}