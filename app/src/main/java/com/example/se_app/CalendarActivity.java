package com.example.se_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        /* 하단 홈 버튼 클릭 시 실행 구문 */
        Button btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
                startActivity(intent); //MainActivity로 이동
            }
        });

        /* 하단 마이페이지 버튼 클릭 시 실행 구문 */
        Button btn_mypage = findViewById(R.id.btn_mypage);
        btn_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, MypageActivity.class);
                startActivity(intent); //MypageActivity로 이동
            }
        });
    }
}