package com.example.widgetapp.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.widgetapp.db.entity.BrushTeethTracker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "brushTeethTracker_db";
    private static Calendar calendar;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        calendar = Calendar.getInstance();
    }

    public List<BrushTeethTracker> getBrushTeethTrackerBetweenDates(String userName, long fromDateMillis, long toDateMillis) {
        ArrayList<BrushTeethTracker> trackerList  = new ArrayList<>();

        fromDateMillis = getTimeInMillisAtDate(fromDateMillis);
        toDateMillis = getTimeInMillisAtDate(toDateMillis);

        String whereClause = BrushTeethTracker.COLUMN_USER_NAME
                + "='" + userName + "' AND " + BrushTeethTracker.COLUMN_DATE + ">=" + fromDateMillis
                + " AND " + BrushTeethTracker.COLUMN_DATE + "<" + toDateMillis;
         String selectQuery = "SELECT * from " + BrushTeethTracker.TABLE_NAME + " WHERE " + whereClause + " ORDER BY " +
                BrushTeethTracker.COLUMN_ID + " DESC";

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        return readCursor(cursor);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(BrushTeethTracker.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BrushTeethTracker.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public Long insertTracker(String name, String firstLastName, boolean brush, Long dateInMillis,
                              int amPm) {

        deleteIfExists(name, dateInMillis, amPm);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int status = 0;
        if (brush) {
            status = 1;
        }

        values.put(BrushTeethTracker.COLUMN_USER_NAME, name);
        values.put(BrushTeethTracker.COLUMN_FIRST_LAST_NAME, firstLastName);
        values.put(BrushTeethTracker.COLUMN_STATUS, status);
        values.put(BrushTeethTracker.COLUMN_DATE, getTimeInMillisAtDate(dateInMillis));
        values.put(BrushTeethTracker.COLUMN_AM_PM, amPm);

        long id = db.insert(BrushTeethTracker.TABLE_NAME, "", values);

        db.close();

        return id;
    }

    private void deleteIfExists(String userName, Long dateInMillis, int amPm) {
        List<BrushTeethTracker> trackers = getBrushTeethTrackerForDate(userName, dateInMillis);
        for (BrushTeethTracker tracker: trackers) {
            if (tracker.getAmPm() == amPm) {
                deleteTrackerId(tracker.getId());
            }
        }

        trackers = getBrushTeethTrackerForDate(userName, dateInMillis);
    }

    private void deleteTrackerId(int id) {
        String whereClause =  " id=?";
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BrushTeethTracker.TABLE_NAME, whereClause, new String[]{String.valueOf(id)});
    }

    @SuppressLint("NewApi")
    private Long getTimeInMillisAtDate(long dateInMillis) {
        long atStartOfDay =  dateInMillis - (dateInMillis % 86400000);
        return  atStartOfDay + 86399999;
    }

    public List<BrushTeethTracker> getBrushTeethTrackerForDate(String userName, Long dateInMillis) {
        long dateInMillisForDay = getTimeInMillisAtDate(dateInMillis);
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns  = new String[] {
                BrushTeethTracker.COLUMN_ID,
                BrushTeethTracker.COLUMN_USER_NAME,
                BrushTeethTracker.COLUMN_FIRST_LAST_NAME,
                BrushTeethTracker.COLUMN_DATE,
                BrushTeethTracker.COLUMN_STATUS,
                BrushTeethTracker.COLUMN_AM_PM};
        String whereClaus= BrushTeethTracker.COLUMN_USER_NAME + "=?" + " AND " + BrushTeethTracker.COLUMN_DATE + "=?";
        String[] whereParameters = {userName, String.valueOf(dateInMillisForDay)};
        Cursor cursor = db.query(false, BrushTeethTracker.TABLE_NAME,
                columns,
                whereClaus, whereParameters,
                null,
                null,
                BrushTeethTracker.COLUMN_ID,
                null,
                null);

        return readCursor(cursor);
    }

    private List<BrushTeethTracker> readCursor(Cursor cursor) {
        List<BrushTeethTracker> trackers = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                try {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(BrushTeethTracker.COLUMN_ID));
                    String userName = cursor.getString(cursor.getColumnIndexOrThrow(BrushTeethTracker.COLUMN_USER_NAME));
                    String firstLastName = cursor.getString(cursor.getColumnIndexOrThrow(BrushTeethTracker.COLUMN_FIRST_LAST_NAME));
                    Long dateInMillis = cursor.getLong(cursor.getColumnIndexOrThrow(BrushTeethTracker.COLUMN_DATE));
                    int status = cursor.getInt(cursor.getColumnIndexOrThrow(BrushTeethTracker.COLUMN_STATUS));
                    int amPm = cursor.getInt(cursor.getColumnIndexOrThrow(BrushTeethTracker.COLUMN_AM_PM));
                    BrushTeethTracker tracker = new BrushTeethTracker(
                            id, userName, firstLastName, dateInMillis, status, amPm);
                    trackers.add(tracker);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                cursor.moveToNext();
            }
            cursor.close();
        }

        return trackers;
    }
}