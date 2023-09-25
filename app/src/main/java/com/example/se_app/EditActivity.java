package com.example.se_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }

    /* 확인 버튼 클릭 시 실행 구문 */
    Button btn_ok = findViewById(R.id.btn_ok);
    


    /* 취소 버튼 클릭 시 실행 구문 */
    Button btn_no = findViewById(R.id.btn_no);


}