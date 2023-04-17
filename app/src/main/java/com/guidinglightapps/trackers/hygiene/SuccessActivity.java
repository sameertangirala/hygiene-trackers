package com.example.widgetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.widgetapp.db.DatabaseHelper;
import com.example.widgetapp.db.entity.BrushTeethTracker;

import java.util.Calendar;
import java.util.List;

public class SuccessActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    private ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        Intent intent = getIntent();

        String userName = intent.getStringExtra("userName");
        String firstLastName = intent.getStringExtra("firstLastName");
        Long dateInMillis = intent.getLongExtra("date", System.currentTimeMillis());
        Boolean brushed = intent.getBooleanExtra("brushed", true);
        Integer amPm = intent.getIntExtra("amPm", 0);
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main  = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
            }
        });



        // write to database
        databaseHelper = new DatabaseHelper(getApplicationContext());

        long id = databaseHelper.insertTracker(userName, firstLastName, brushed, dateInMillis, amPm);
        Toast.makeText(getApplicationContext(), "Inserted log id " + id, Toast.LENGTH_LONG);

        List<BrushTeethTracker> dates = databaseHelper.getBrushTeethTrackerForDate("nikhilt",
               dateInMillis);
    }
}