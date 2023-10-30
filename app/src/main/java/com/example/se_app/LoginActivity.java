package com.example.se_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se_app.dto.LoginDTO;
import com.example.se_app.instance.RetrofitInstance;
import com.example.se_app.service.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Service service = RetrofitInstance.getRetrofitInstance().create(Service.class);

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText et_id = findViewById(R.id.et_id); //아이디 입력 칸
        EditText et_pw = findViewById(R.id.et_pw); //비밀번호 입력 칸

        /* 로그인 버튼 클릭 시 실행 구문 */
        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<LoginDTO.LoginResponse> call = service.login(new LoginDTO.LoginRequest(et_id.getText().toString(), et_pw.getText().toString()));
                call.enqueue(new Callback<LoginDTO.LoginResponse>() {
                    //서버와 통신 성공
                    @Override
                    public void onResponse(Call<LoginDTO.LoginResponse> call, Response<LoginDTO.LoginResponse> response) {
                        //응답 성공
                        if (response.isSuccessful()) {
                            //body에 있는 토큰 값 저장
                            String token = response.body().getToken();

                            //SharedPreferences로 토큰 저장
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("jwt_token", token);
                            myEdit.apply();

                            //MainActivity로 이동
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        //응답 실패
                        else {

                        }
                    }

                    //서버와 통신 실패
                    @Override
                    public void onFailure(Call<LoginDTO.LoginResponse> call, Throwable t) {

                    }
                });
            }
        });
        
        /* 회원가입 버튼 클릭 시 실행 구문 */
        Button btn_register = findViewById(R.id.btn_confirm);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent); //RegisterActivity로 이동
            }
        });

    }

}