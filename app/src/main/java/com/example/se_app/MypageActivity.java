package com.example.se_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MypageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        /* 정보 수정 버튼 클릭 시 실행 구문 */
        Button btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, EditActivity.class);
                startActivity(intent); //EditActivity로 이동
            }
        });

        /* 하단 캘린더 버튼 클릭 시 실행 구문 */
        Button btn_calendar = findViewById(R.id.btn_calendar);
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, CalendarActivity.class);
                startActivity(intent); //CalendarActivity로 이동
            }
        });

        /* 하단 홈 버튼 클릭 시 실행 구문 */
        Button btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MypageActivity.this, MainActivity.class);
                startActivity(intent); //MainActivity로 이동
            }
        });

    }
}