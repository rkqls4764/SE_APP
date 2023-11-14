package com.example.se_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se_app.dto.RecordDTO;
import com.example.se_app.instance.RetrofitInstance;
import com.example.se_app.service.Service;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private SharedPreferences sharedPreferences;    //SharedPreferences에서 토큰 가져오기
    private TextView tv_time;

    Service service = RetrofitInstance.getRetrofitInstance().create(Service.class);

    /* SharedPreferences에서 토큰을 가져오는 함수 */
    String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String token = sharedPreferences.getString("jwt_token", "");
        return token;
    }

    //당일 기록 가져오기
    void getTodayRecord() {
        String token = getToken();
        Call<RecordDTO.record> call = service.todayrecord("Bearer " + token);
        call.enqueue(new Callback<RecordDTO.record>() {
            //서버 통신 성공
            @Override
            public void onResponse(Call<RecordDTO.record> call, Response<RecordDTO.record> response) {
                //응답 성공(200)
                if (response.isSuccessful()) {
                    //당일 기록 저장
                    int recordTime = response.body().getRecordTimeToday();


                }
            }

            //서버 통신 실패
            @Override
            public void onFailure(Call<RecordDTO.record> call, Throwable t) {
                Toast.makeText(MainActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "서버 통신 실패: " + t.getMessage());
            }
        });
    }

    //기록하기(출석하기)
    void record() {
        String token = getToken();

        Call<RecordDTO.Record> call = service.record(token, record);
        call.enqueue(new Callback<RecordDTO.Record>() {
            @Override
            public void onResponse(Call<RecordDTO.Record> record, Response<RecordDTO.Record> response) {
                if (response.isSuccessful()) {
                    // 응답 성공(200)
                    String Latitude = response.body().setUserLatitude();
                    String Longitude = response.body().setUserLongitude();
                    String message = "기록 시작합니다.";
                    Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                } else {
                    // 응답 실패(403)
                    String message = "동아리방에서 출석해 주세요";
                    Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RecordDTO.Record> record, Throwable t) {
                // 서버와 통신이 실패
            }
        });
    }

    //기록중단
    void stopRecord() {
        String token = getToken();

        Call<RecordDTO.Record> call = service.record(token, record);
        call.enqueue(new Callback<RecordDTO.Record>() {
            @Override
            public void onResponse(Call<RecordDTO.Record> record, Response<RecordDTO.Record> response) {
                if (response.isSuccessful()) {
                    // 응답 성공(200)
                } else {
                    // 응답 실패(403)
                    String message = "동아리방에서 출석해 주세요";
                    Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RecordDTO.Record> record, Throwable t) {
                // 서버와 통신이 실패
            }
        });
    }

    //위치 보내기
    void recordLocation() {
        String token = getToken();

        //현재 위치 값을 여기 넣어!!!!!!!!!!!!!!
        Double userLatitude = 0.0; //경도
        Double userLongitude = 0.0; //위도

        RecordDTO.locationRequest locationRequest = new RecordDTO.locationRequest(userLatitude, userLongitude);

        Call<RecordDTO.locationResponse> call = service.record("Bearer " + token, locationRequest);
        call.enqueue(new Callback<RecordDTO.locationResponse>() {
            //서버 통신 성공
            @Override
            public void onResponse(Call<RecordDTO.locationResponse> call, Response<RecordDTO.locationResponse> response) {
                //응답 성공(200)
                if (response.isSuccessful()) {
                    //성공 메세지 저장
                    String message = response.body().getMessage();
                    //토스트 출력
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                //(403)
                else if (response.code() == 403) {
                    //에러 메세지 저장
                    String message = response.body().getMessage();
                    //토스트 출력
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            //서버 통신 실패
            @Override
            public void onFailure(Call<RecordDTO.locationResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "서버 통신 실패: " + t.getMessage());
            }
        });
    }

    private boolean isrunning = true;
    private Thread timeThread = null;
    private Boolean isRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_info = findViewById(R.id.btn_mypage);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //마이페이지 화면으로 이동
                Intent intent = new Intent(MainActivity.this, MypageActivity.class);
                startActivity(intent);
            }
        });

        // 타이머 구현
        tv_time = findViewById(R.id.tv_time);
        Button btn_start = findViewById(R.id.btn_start);
        Button btn_stop = findViewById(R.id.btn_stop);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isrunning) {
                    isrunning = true;
                    btn_start.setVisibility(View.INVISIBLE);
                    btn_stop.setVisibility(View.VISIBLE);

                    timeThread = new Thread(new timeThread());
                    timeThread.start();
                }
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isrunning) {
                    isrunning = false;
                    btn_stop.setVisibility(View.INVISIBLE);
                    btn_start.setVisibility(View.VISIBLE);

                    tv_time.setText("");
                    timeThread.interrupt();
                }
            }
        });
        Button btn_calendar = findViewById(R.id.btn_calendar);
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //캘린더 화면으로 이동
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        Button btn_rank = findViewById(R.id.btn_rank);
        btn_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //랭크 화면으로 이동
                Intent intent = new Intent(MainActivity.this, RankActivity.class);
                startActivity(intent);
            }
        });
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int sec = (msg.arg1 / 100) % 60;
            int min = (msg.arg1 / 100) / 60;
            int hour = (msg.arg1 / 100) / 360;
            //1000이 1초 1000*60 은 1분 1000*60*10은 10분 1000*60*60은 한시간

            @SuppressLint("DefaultLocale") String result = String.format("%02d:%02d:%02d", hour, min, sec);

            tv_time.setText(result);
        }
    };
    public class timeThread implements Runnable {
        @Override
        public void run() {
            int i = 0;

            while (true) {
                //isRunning 수정
                while (isRunning) {
                    Message msg = new Message();
                    msg.arg1 = i++;
                    handler.sendMessage(msg);

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_time.setText("");
                                tv_time.setText("00:00:00");
                            }
                        });
                        return; // 인터럽트 받을 경우 return
                    }
                }
            }
        }
    }

}