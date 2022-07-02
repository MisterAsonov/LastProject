package com.example.lastproject;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Utils {

    final static String DATABASE_NAME = "db_sqlite";

    final static String TABLE_NAME_USER = "tbl_user";

    final static String TABLE_USER_COL_EMAIL = "email";
    final static String TABLE_USER_COL_NAME = "name";
    final static String TABLE_USER_COL_LASTNAME = "lastname";
    final static String TABLE_USER_COL_WHO = "who";
    final static String TABLE_USER_COL_REFERALLINK = "referal_link";
    final static String TABLE_USER_COL_UID = "uid";
    final static String TABLE_USER_COL_MINMAGEURL = "mImageUrl";

    final static String TABLE_NAME_REPORT = "tbl_report";
    final static String TABLE_REPORT_COL_TITEL = "titel";
    final static String TABLE_REPORT_COL_EXPLANATION = "explanation";
    final static String TABLE_REPORT_COL_STATUS = "status";
    final static String TABLE_REPORT_COL_DATE = "date";
    final static String TABLE_REPORT_COL_ROOM = "room";
    final static String TABLE_REPORT_COL_BUILDING = "building";
    final static String TABLE_REPORT_COL_CREATOR_ID = "creator_id";


    public static void createTables(SQLiteDatabase db) {
        db.execSQL("create table if not exists "
                + Utils.TABLE_NAME_REPORT +
                "("+TABLE_REPORT_COL_TITEL + " text,"+
                Utils.TABLE_REPORT_COL_EXPLANATION + " text, "+
                Utils.TABLE_REPORT_COL_STATUS + " text, "+
                Utils.TABLE_REPORT_COL_DATE + " text,"+
                Utils.TABLE_REPORT_COL_ROOM + "text,"+
                Utils.TABLE_REPORT_COL_BUILDING + " text,"+
                Utils.TABLE_REPORT_COL_CREATOR_ID + " text)");

        db.execSQL("create table if not exists "
                + TABLE_NAME_USER +
                "(" + TABLE_USER_COL_EMAIL + " TEXT," +
                "" + Utils.TABLE_USER_COL_NAME + " TEXT," +
                "" + Utils.TABLE_USER_COL_LASTNAME + " TEXT," +
                "" + Utils.TABLE_USER_COL_WHO + " TEXT," +
                "" + Utils.TABLE_USER_COL_REFERALLINK + " TEXT," +
                "" + Utils.TABLE_USER_COL_UID + " TEXT," +
                "" + Utils.TABLE_USER_COL_MINMAGEURL + " TEXT)");

    }

}
