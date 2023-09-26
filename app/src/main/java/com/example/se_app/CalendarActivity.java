package com.example.se_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

        /* 캘린더에서 날짜 선택 시 실행 구문 */
        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                /* TextView를 선택 날짜로 설정 */
                TextView tv_day = findViewById(R.id.tv_day);
                tv_day.setText(year + "년 " + month + "월 " + day + "일");
            }
        });





    }
}