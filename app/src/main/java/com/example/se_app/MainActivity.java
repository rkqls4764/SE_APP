package com.example.se_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;

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
        Call<RecordDTO.TodayRecord> call = service.todayrecord("Bearer " + token);
        call.enqueue(new Callback<RecordDTO.TodayRecord>() {
            //서버 통신 성공
            @Override
            public void onResponse(Call<RecordDTO.TodayRecord> call, Response<RecordDTO.TodayRecord> response) {
                //응답 성공(200)
                if (response.isSuccessful()) {
                    //당일 기록 저장
                    int recordTime = response.body().getRecordTimeToday();
                }
            }
            //서버 통신 실패
            @Override
            public void onFailure(Call<RecordDTO.TodayRecord> call, Throwable t) {
                Toast.makeText(MainActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "서버 통신 실패: " + t.getMessage());
            }
        });
    }

    //기록하기(출석하기)
    void startRecord() {
        String token = getToken();
        Double setUserLatitude = 0.0;
        Double setUserLongitude = 0.0;

        Call<RecordDTO.StartRecord> call = service.startrecord("Bearer " + token, setUserLatitude, setUserLongitude);
        call.enqueue(new Callback<RecordDTO.StartRecord>() {
            @Override
            public void onResponse(Call<RecordDTO.StartRecord> record, Response<RecordDTO.StartRecord> response) {
                if (response.isSuccessful()) {
                    // 응답 성공(200)
                    Double Latitude = response.body().getUserLatitude();
                    Double Longitude = response.body().getUserLongitude();
                    String message = response.body().getMessage();
                    Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                } else {
                    // 응답 실패(403)
                    String message = response.body().getMessage();
                    Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RecordDTO.StartRecord> record, Throwable t) {
                // 서버와 통신이 실패
                Toast.makeText(MainActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "서버 통신 실패: " + t.getMessage());
            }
        });
    }

    //기록중단
    void stopRecord() {
        String token = getToken();

        Call<RecordDTO.StopRecord> call = service.stoprecord("Bearer " + token);
        call.enqueue(new Callback<RecordDTO.StopRecord>() {
            @Override
            public void onResponse(Call<RecordDTO.StopRecord> record, Response<RecordDTO.StopRecord> response) {
                if (response.isSuccessful()) {
                    // 응답 성공(200)
                    int recordTime = response.body().getRecordTime();
                    Double userLatitude = response.body().getUserLatitude();
                    Double userLongitude = response.body().getUserLongitude();
                    String message = response.body().getMessage();
                    Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                } else {
                    // 응답 실패(403)
                    String message = response.body().getMessage();
                    Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RecordDTO.StopRecord> record, Throwable t) {
                // 서버와 통신이 실패
                Toast.makeText(MainActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "서버 통신 실패: " + t.getMessage());
            }
        });
    }

    //위치 보내기
    void Location() {
        String token = getToken();

        //현재 위치 값을 여기 넣어!!!!!!!!!!!!!!
        Double memberLatitude = 0.0; //경도
        Double memberLongitude = 0.0; //위도
        int recordtime = 0;
        RecordDTO.Location location = new RecordDTO.Location(recordtime, memberLatitude, memberLongitude);

        Call<RecordDTO.Location> call = service.location("Bearer " + token, location);
        call.enqueue(new Callback<RecordDTO.Location>() {
            //서버 통신 성공
            @Override
            public void onResponse(Call<RecordDTO.Location> call, Response<RecordDTO.Location> response) {
                //응답 성공(200)
                if (response.isSuccessful()) {
                    Double memberLatitude = response.body().getMemberLatitude();
                    Double memberLongitude = response.body().getMemberLongitude();
                    String message = response.body().getMessage();
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    // 응답 실패(401)
                    String message = response.body().getMessage();
                    //토스트 출력
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            //서버 통신 실패
            @Override
            public void onFailure(Call<RecordDTO.Location> call, Throwable t) {
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
        chronometer = findViewById(androidx.core.R.id.chronometer);
        chronometer.setFormat("%s");

        Button btn_start = findViewById(R.id.btn_start);
        Button btn_stop = findViewById(R.id.btn_stop);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isrunning) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    isrunning = true;
                    btn_start.setVisibility(View.INVISIBLE);
                    btn_stop.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isrunning) {
                    chronometer.stop();
                    isrunning = false;
                    btn_stop.setVisibility(View.INVISIBLE);
                    btn_start.setVisibility(View.VISIBLE);
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
                    }
                }
            }
        }
    }

}