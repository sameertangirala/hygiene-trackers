package com.example.widgetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class LogActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Button yesButton;
    private Button noButton;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log);

        Intent intent = getIntent();

        String userName = intent.getStringExtra("userName");
        String firstLastName = intent.getStringExtra("firstLastName");

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate(calendar));

        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent amPm  = new Intent(getApplicationContext(), AmPmActivity.class);
                amPm.putExtra("userName", userName);
                amPm.putExtra("firstLastName", firstLastName);
                amPm.putExtra("date", getTodaysDateInMillis(calendar));
                amPm.putExtra("brushed", true);
                startActivity(amPm);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tryAgainActivity  = new Intent(getApplicationContext(), TryAgainActivity.class);
                tryAgainActivity.putExtra("userName", userName);
                startActivity(tryAgainActivity);
            }
        });

    }

    private String getTodaysDate(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private long getTodaysDateInMillis(Calendar calendar) {
        Toast.makeText(getApplicationContext(), String.valueOf(calendar.getTimeInMillis()), Toast.LENGTH_LONG);
        return calendar.getTimeInMillis();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                calendar.set(year, month-1, day, 0, 0, 0);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1) {
            return "JAN";
        }
        if (month == 2) {
            return "FEB";
        }
        if (month == 3) {
            return "MAR";
        }
        if (month == 4) {
            return "APR";
        }
        if (month == 5) {
            return "MAY";
        }
        if (month == 6) {
            return "JUN";
        }
        if (month == 7) {
            return "JUL";
        }
        if (month == 8) {
            return "AUG";
        }
        if (month == 9) {
            return "SEP";
        }
        if (month == 10) {
            return "OCT";
        }
        if (month == 11) {
            return "NOV";
        }
        if (month == 12) {
            return "DEC";
        }
        return "JAN";


    }

    public void openDatePicker(View view) {
        datePickerDialog.show();

    }

}