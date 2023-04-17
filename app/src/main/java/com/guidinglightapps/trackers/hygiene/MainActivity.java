package com.example.widgetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
        Button log;
        Button summary;
        TextView txt;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            txt = findViewById(R.id.welcome_text);
            log = findViewById(R.id.log);
            summary = findViewById(R.id.summary);

            log.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent logActivity  = new Intent(getApplicationContext(), LogActivity.class);
                    logActivity.putExtra("userName", "nikhilt");
                    logActivity.putExtra("firstLastName", "Nikhil Tangirala");
                    startActivity(logActivity);
                }
            });

            summary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent summaryActivity  = new Intent(getApplicationContext(), SummaryActivity.class);
                    summaryActivity.putExtra("userName", "nikhilt");
                    startActivity(summaryActivity);
                }
            });


        }
   }