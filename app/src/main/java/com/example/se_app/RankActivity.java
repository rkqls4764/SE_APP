package com.example.se_app;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RankActivity extends AppCompatActivity {

    Button btn_time = findViewById(R.id.btn_time);
    Button btn_day = findViewById(R.id.btn_day);
    //hi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
    }

}