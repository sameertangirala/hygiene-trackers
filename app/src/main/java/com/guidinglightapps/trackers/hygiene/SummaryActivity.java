package com.example.widgetapp;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.example.widgetapp.db.DatabaseHelper;
import com.example.widgetapp.db.entity.BrushTeethTracker;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SummaryActivity extends AppCompatActivity {
    CalendarView calendarView;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.showCurrentMonthPage();

        Intent intent = getIntent();

        String userName = intent.getStringExtra("userName");


        loadEvents(userName);
        calendarView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                loadEvents(userName);
            }
        });
    }

    private void loadEvents(String userName) {
        int month = calendarView.getCurrentPageDate().get(Calendar.MONTH);
        int year = calendarView.getCurrentPageDate().get(Calendar.YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        long fromMillis  = calendar.getTimeInMillis();
        calendar.set(year, month+1, 1);
        long toMillis = calendar.getTimeInMillis();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        List<BrushTeethTracker> brushTeethTrackerList = databaseHelper.getBrushTeethTrackerBetweenDates(userName, fromMillis, toMillis);
        setEvents(calendarView, calendar, brushTeethTrackerList);
    }

    @SuppressLint("NewApi")
    private void setEvents(CalendarView calendarView, Calendar calendar, List<BrushTeethTracker> brushTeethTrackerList) {
        List<EventDay> events = new ArrayList<>();

        Map<Long, Integer> twiceMap = new HashMap<>();
        for (BrushTeethTracker brushTeethTracker : brushTeethTrackerList) {
            long dateInMillis = brushTeethTracker.getDateInMillis();
            if (twiceMap.containsKey(dateInMillis)) {
                twiceMap.put(dateInMillis, 2);
            } else {
                twiceMap.put(dateInMillis, 1);
            }
        }

        for (Map.Entry<Long, Integer> entry : twiceMap.entrySet()) {
            if (entry.getValue() >= 2) {
                long dateInTimeMillis = entry.getKey();
                LocalDate date = Instant.ofEpochMilli(dateInTimeMillis).atZone(ZoneId.systemDefault()).toLocalDate();

                Calendar eventCalendar = Calendar.getInstance();
                eventCalendar.setTimeInMillis(dateInTimeMillis);
                EventDay eventDay = new EventDay(eventCalendar, R.drawable.brushed_teeth_twice);
                events.add(eventDay);
            }
        }

        calendarView.setEvents(events);
    }

    private long getTodaysDateInMillis(Calendar calendar) {
        Toast.makeText(getApplicationContext(), String.valueOf(calendar.getTimeInMillis()), Toast.LENGTH_LONG);
        return calendar.getTimeInMillis();
    }

}