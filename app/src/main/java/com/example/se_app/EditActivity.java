package com.example.se_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se_app.dto.MypageDTO;
import com.example.se_app.instance.RetrofitInstance;
import com.example.se_app.service.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    Service service = RetrofitInstance.getRetrofitInstance().create(Service.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        TextView tv_studentId = findViewById(R.id.et_studentId); //학번(아이디)
        TextView tv_password = findViewById(R.id.et_password); //비밀번호
        TextView tv_name = findViewById(R.id.et_name); //이름
        TextView tv_major = findViewById(R.id.et_major); //학과
        TextView tv_birth = findViewById(R.id.et_birth); //생년월일
        Spinner sp_state = findViewById(R.id.spinner); //상태(재학/휴학/졸업)

        //SharedPreferences에서 토큰 가져오기
        String token = getToken();

        //사용자 정보 가져오기
        getUserData(token, tv_studentId, tv_password, tv_name, tv_major, tv_birth, sp_state);

        //확인 버튼 클릭 시 실행
        clickBtnOk(token);

        //취소 버튼 클릭 시 실행 구문
        clickBtnNo();
    }

    /* SharedPreferences에서 토큰을 가져오는 함수 */
    String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String token = sharedPreferences.getString("jwt_token", "");
        return token;
    }

    /* 사용자의 정보를 가져오는 함수 */
    void getUserData(String token, TextView tv_studentId, TextView tv_password, TextView tv_name, TextView tv_major, TextView tv_birth, Spinner sp_state) {
        Call<MypageDTO.MypageResponse> call = service.mypage("Bearer " + token);
        call.enqueue(new Callback<MypageDTO.MypageResponse>() {
            //서버와 통신 성공
            @Override
            public void onResponse(Call<MypageDTO.MypageResponse> call, Response<MypageDTO.MypageResponse> response) {
                //응답 성공(200): 데이터베이스에서 회원 정보를 제대로 읽어왔을 때
                if (response.isSuccessful()) {
                    //정보 출력
                    tv_studentId.setText("학번 : " + response.body().getUserId());
                    tv_password.setText("비밀번호 : " + response.body().getUserPw());
                    tv_name.setText("이름 : " + response.body().getUserName());
                    tv_major.setText("학과 : " + response.body().getUserMajor());
                    tv_birth.setText("생년월일 : " + localDateToString(response.body().getUserBirth()));
                    String state = response.body().getUserState();
                    sp_state.setSelection(getIndex(sp_state, state));
                }
                //응답 실패(505): 데이터베이스 오류
                else if (response.code() == 505) {
                    //body의 에러 메세지를 저장
                    String message = response.body().getMessage();

                    //에러 메세지를 토스트 메세지로 띄움
                    Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            //서버와 통신 실패
            @Override
            public void onFailure(Call<MypageDTO.MypageResponse> call, Throwable t) {
                Toast.makeText(EditActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "서버 통신 실패: " + t.getMessage());
            }
        });
    }

    /* 확인 버튼 클릭 시 실행 함수 */
    void clickBtnOk(String token) {

        Button btn_ok = findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<MypageDTO.MypageResponse> call = service.edit("Bearer " + token);
                call.enqueue(new Callback<MypageDTO.MypageResponse>() {
                    //서버와 통신 성공
                    @Override
                    public void onResponse(Call<MypageDTO.MypageResponse> call, Response<MypageDTO.MypageResponse> response) {
                        //응답 성공(200):
                        if (response.isSuccessful()) {

                        }
                        //응답 실패(505): 데이터베이스 오류
                        else if (response.code() == 505) {
                            //body의 에러 메세지를 저장
                            String message = response.body().getMessage();

                            //에러 메세지를 토스트 메세지로 띄움
                            Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    //서버와 통신 실패
                    @Override
                    public void onFailure(Call<MypageDTO.MypageResponse> call, Throwable t) {
                        Toast.makeText(EditActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "서버 통신 실패: " + t.getMessage());
                    }
                });

                //MypageActivity로 이동
                Toast.makeText(EditActivity.this, "회원 정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditActivity.this, MypageActivity.class);
                startActivity(intent);
            }
        });
    }

    /* 취소 버튼 클릭 시 실행 함수 */
    void clickBtnNo() {
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

    /* LocalDate(yyyy-MM-dd) -> String(yyMMdd)로 바꾸는 함수 */
    private String localDateToString(LocalDate dateLocalDate) {
        DateTimeFormatter formatter = null;
        String dateString = null;

        //Android 8.0 이상 버전에서만 실행 가능
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyMMdd");
            dateString = dateLocalDate.format(formatter);
        }

        return dateString;
    }

    /* state 초기 값의 번호를 찾아주는 함수 */
    private int getIndex(Spinner sp, String item) {
        for (int i = 0; i < sp.getCount(); i++) {
            if (sp.getItemAtPosition(i).toString().equalsIgnoreCase(item)) {
                return i;
            }
        }
        return 0;
    }

}