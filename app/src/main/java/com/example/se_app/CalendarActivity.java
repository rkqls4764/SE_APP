package com.example.se_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se_app.instance.RetrofitInstance;
import com.example.se_app.service.Service;

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    Service service = RetrofitInstance.getRetrofitInstance().create(Service.class);

    /* 화면 시작 시 실행 함수 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        /*
        - 공지사항 가져오기
        - 목표시간 가져오기
        - 캘린더 초기 값 설정
        - 캘린더 날짜 선택 시 해당 날짜 정보 가져오기
         */

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

        // 하단 홈 버튼 클릭 시 실행
        clickBtnHome();

        // 하단 마이페이지 버튼 클릭 시 실행
        clickBtnMypage();

    }

    /* 하단 홈 버튼 클릭 시 실행 함수 */
    void clickBtnHome() {
        Button btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MainActivity로 이동
                Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /* 하단 마이페이지 버튼 클릭 시 실행 함수 */
    void clickBtnMypage() {
        Button btn_mypage = findViewById(R.id.btn_mypage);
        btn_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MypageActivity로 이동
                Intent intent = new Intent(CalendarActivity.this, MypageActivity.class);
                startActivity(intent);
            }
        });
    }
}