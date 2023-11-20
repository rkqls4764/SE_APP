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

public class DayActivity extends AppCompatActivity {

    Button btn_time = findViewById(R.id.btn_time);
    Button btn_day = findViewById(R.id.btn_day);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);


        Call<RecordDTO.Rank> call = Service.dayrank();
        call.enqueue(new Callback<RecordDTO.Rank>() {
            @Override
            public void onResponse(Call<RecordDTO.Rank> call, Response<RecordDTO.Rank> response) {
                if( response.isSuccessful()) {
                    for(int i = 0; i < 5; i++) {
                        String memberId = response.body().getMemberId();
                        String memberName = response.body().getMemberName();
                        String memberMajor = response.body().getMemberMajor();
                        int recordTime = response.body().getRecordTime();
                    }
                }
                else {
                    //404 error
                    String message = "저장된 회원 정보가 없습니다.";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                }
            }
            //서버와 통신 실패
            @Override
            public void onFailure(Call<RecordDTO.Rank> call, Throwable t) {
                String message = "서버와 연결이 끊겼습니다.";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

        });
    }
}