package com.example.widgetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class AmPmActivity extends AppCompatActivity {

    private Switch switchView;
    private TextView AM;
    private TextView PM;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_pm);

        Intent intent = getIntent();

        String userName = intent.getStringExtra("userName");
        String firstLastName = intent.getStringExtra("firstLastName");
        Boolean brushed = intent.getBooleanExtra("brushed", true);
        Long dateInMillis = intent.getLongExtra("date", System.currentTimeMillis());


        switchView = findViewById(R.id.simpleSwitch1);
        AM = findViewById(R.id.am);
        PM = findViewById(R.id.pm);
        button = findViewById(R.id.submit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amPm=0;
                if (switchView.isChecked()) {
                    amPm=1;
                }
                Intent successActivity  = new Intent(getApplicationContext(), SuccessActivity.class);
                successActivity.putExtra("userName", userName);
                successActivity.putExtra("firstLastName", firstLastName);
                successActivity.putExtra("firstLastName", firstLastName);
                successActivity.putExtra("brushed", brushed);
                successActivity.putExtra("amPm", amPm);
                successActivity.putExtra("date", dateInMillis);
                startActivity(successActivity);
            }
        });

    }


}