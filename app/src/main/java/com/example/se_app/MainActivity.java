package com.example.se_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btn_info = findViewById(R.id.btn_mypage);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //마이페이지 화면으로 이동
                Intent intent = new Intent(MainActivity.this, MypageActivity.class);
                startActivity(intent);
            }
        });

        Button btn_attend = findViewById(R.id.btn_start);
        btn_attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //기록 중단 버튼으로 변경
            }
        });


        Button btn_calendar = findViewById(R.id.btn_calendar);
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //캘린더 화면으로 이동
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        Button btn_rank = findViewById(R.id.btn_rank);
        btn_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //랭크 화면으로 이동
                Intent intent = new Intent(MainActivity.this, RankActivity.class);
                startActivity(intent);
            }
        });

    }
}