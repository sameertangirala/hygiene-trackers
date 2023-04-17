package com.example.widgetapp.db.entity;

import java.util.Date;

public class BrushTeethTracker {

    public static final String TABLE_NAME = "brush_teeth_tracker";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_FIRST_LAST_NAME = "first_last_name";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_AM_PM = "am_pm";
    public static final String COLUMN_STATUS = "status";

    private int id;
    private String userName;
    private String firstLastName;
    private Long dateInMillis;
    private int status;
    private int amPm;// 0 for AM, 1 for PM


    public BrushTeethTracker() {}
    public BrushTeethTracker(int id, String userName, String firstLastName, Long dateInMillis, int status,
                             int amPm) {
        this.id = id;
        this.userName = userName;
        this.firstLastName = firstLastName;
        this.dateInMillis = dateInMillis;
        this.status = status;
        this.amPm = amPm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public Long getDateInMillis() {
        return dateInMillis;
    }

    public void setDateInMillis(Long timeInMillis) {
        this.dateInMillis = dateInMillis;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAmPm() {
        return amPm;
    }

    public void setAmPm(int amPm) {
        this.amPm = amPm;
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_USER_NAME + " TEXT,"
                    + COLUMN_FIRST_LAST_NAME + " TEXT,"
                    + COLUMN_DATE + " LONG,"
                    + COLUMN_STATUS + " INTEGER,"
                    + COLUMN_AM_PM + " INTEGER)";
}