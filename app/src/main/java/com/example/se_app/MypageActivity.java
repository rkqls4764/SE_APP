package com.example.se_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se_app.databinding.ActivityMypageBinding;

public class MypageActivity extends AppCompatActivity {

    private ActivityMypageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMypageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* 뒤로가기 버튼 클릭 시 실행 구문 */
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MypageActivity.super.onBackPressed(); //MainActivity로 이동(뒤로가기)
            }
        });

        /* 비밀번호 변경 버튼 클릭 시 실행 구문 */
        binding.btnChangePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MypageActivity.this, "비밀번호 변경", Toast.LENGTH_SHORT).show();
            }
        });

    }
}