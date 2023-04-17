package com.example.widgetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TryAgainActivity extends AppCompatActivity {

    private ImageButton image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_again);

        Intent intent = getIntent();
        image = findViewById(R.id.home);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main  = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
            }
        });



        String userName = intent.getStringExtra("userName");
    }
}