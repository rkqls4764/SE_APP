package com.example.se_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se_app.dto.LoginDTO;
import com.example.se_app.instance.RetrofitInstance;
import com.example.se_app.service.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    Service service = RetrofitInstance.getRetrofitInstance().create(Service.class);
    private SharedPreferences sharedPreferences;

    /* 로그인 화면 시작 시 실행 함수 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText et_id = findViewById(R.id.et_id); //아이디 입력 칸
        EditText et_pw = findViewById(R.id.et_pw); //비밀번호 입력 칸

        //로그인 버튼 클릭 시 실행
        clickBtnLogin(et_id, et_pw);
        
        //회원가입 버튼 클릭 시 실행
        clickBtnRegister();
    }

    /* 로그인 버튼 클릭 시 실행 함수 */
    void clickBtnLogin(EditText et_id, EditText et_pw) {

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginDTO.LoginRequest loginRequest = new LoginDTO.LoginRequest(et_id.getText().toString(), et_pw.getText().toString());
                Call<LoginDTO.LoginResponse> call = service.login(loginRequest);
                call.enqueue(new Callback<LoginDTO.LoginResponse>() {
                    //서버와 통신 성공
                    @Override
                    public void onResponse(Call<LoginDTO.LoginResponse> call, Response<LoginDTO.LoginResponse> response) {
                        //응답 성공(200): 아이디와 비밀번호가 모두 일치할 때
                        if (response.isSuccessful()) {
                            //body에 있는 토큰 값 저장
                            String token = response.body().getToken();

                            //SharedPreferences로 토큰 저장
                            setToken(token);

                            //MainActivity로 이동
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        //응답 실패(404): 아이디가 일치하지 않을 때
                        else if (response.code() == 404) {

                        }
                    }

                    //서버와 통신 실패
                    @Override
                    public void onFailure(Call<LoginDTO.LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "서버 통신 실패: " + t.getMessage());
                    }
                });
            }
        });
    }

    /* 회원가입 버튼 클릭 시 실행 함수 */
    void clickBtnRegister() {
        Button btn_register = findViewById(R.id.btn_confirm);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //RegisterActivity로 이동
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /* SharedPreferences로 토큰을 저장하는 함수 */
    void setToken(String token) {
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("jwt_token", token);
        myEdit.apply();
    }
}