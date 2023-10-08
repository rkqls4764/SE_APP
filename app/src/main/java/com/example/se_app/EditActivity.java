package com.example.se_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        /* 확인 버튼 클릭 시 실행 구문 */
        Button btn_ok = findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MypageActivity로 이동
                Intent intent = new Intent(EditActivity.this, MypageActivity.class);
                startActivity(intent);
            }
        });

        /* 취소 버튼 클릭 시 실행 구문 */
        Button btn_no = findViewById(R.id.btn_no);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MypageActivity로 이동(뒤로가기)
                Toast.makeText(EditActivity.this, "정보 수정을 취소합니다.", Toast.LENGTH_SHORT).show();
                EditActivity.super.onBackPressed();
            }
        });

    }
}