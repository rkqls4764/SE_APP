package com.example.se_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se_app.dto.RankDTO;
import com.example.se_app.dto.RecordDTO;
import com.example.se_app.service.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    Button btn_day = findViewById(R.id.btn_day);
    Button btn_time = findViewById(R.id.btn_time);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        Call<RankDTO.RankResponse> call = service.timerank;
        call.enqueue(new Callback<RankDTO.RankResponse>() {
            @Override
            public void onResponse(Call<RankDTO.RankResponse> call, Response<RankDTO.RankResponse> response) {
                if( response.isSuccessful()) {
                    for(int i = 0; i < 5; i++) {
                        String memberId = response.body().getMemberId();
                        String memberName = response.body().getMemberName();
                        String memberMajor = response.body().getMemberMajor();
                        int recordTime = response.body().getRecordTime();
                    }
                }
                else {
                    // 응답 실패(404)
                    String message = response.body().getMessage();
                    Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();

                }
            }
            //서버와 통신 실패
            @Override
            public void onFailure(Call<RankDTO.RankResponse> call, Throwable t) {
                Toast.makeText(TimeActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "서버 통신 실패: " + t.getMessage());
            }

        });
    }
}