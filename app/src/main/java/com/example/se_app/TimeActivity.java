package com.example.se_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se_app.dto.RecordDTO;
import com.example.se_app.service.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeActivity extends AppCompatActivity {

    Button btn_day = findViewById(R.id.btn_day);
    Button btn_time = findViewById(R.id.btn_time);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
/*
        Call<RecordDTO.TimeRank> call = Service.timerank();
        call.enqueue(new Callback<RecordDTO.TimeRank>() {
            @Override
            public void onResponse(Call<RecordDTO.TimeRank> call, Response<RecordDTO.TimeRank> response) {
                if( response.isSuccessful()) {
                    String msg = response.body().getClass();
                    //학생 없을 때 있을 때 나눠서 출력 구문
                    //학생 데이터 정보를 정렬해서 주는지 아닌지
                }
                else {
                    String errormsg = "오류 발생";
                    Toast.makeText(getApplicationContext(), errormsg, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<RecordDTO.TimeRank> call, Throwable t) {
                String errormsg = "서버와 연결이 끊겼습니다.";
                Toast.makeText(getApplicationContext(), errormsg, Toast.LENGTH_SHORT).show();


            }
        });
*/
    }
}