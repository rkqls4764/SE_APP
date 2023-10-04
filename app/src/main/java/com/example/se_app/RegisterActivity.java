package com.example.se_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se_app.dto.RegisterDTO;
import com.example.se_app.instance.RetrofitInstance;
import com.example.se_app.service.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity {

    Service service = RetrofitInstance.getRetrofitInstance().create(Service.class);

    String state; //상태(재학/휴학/졸업)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText et_studentId = findViewById(R.id.et_studentId); //학번(아이디)
        EditText et_password = findViewById(R.id.et_password); //비밀번호
        EditText et_name = findViewById(R.id.et_name); //이름
        EditText et_major = findViewById(R.id.et_major); //학과
        EditText et_birth = findViewById(R.id.et_birth); //생년월일
        Spinner sp_state = findViewById(R.id.sp_state); //상태(재학/휴학/졸업)

        /* 상태 리스트의 아이템 클릭 시 실행 구문 */
        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state = parent.getItemAtPosition(position).toString();
            }

            //아무것도 선택되지 않을 경우
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                state = "";
            }
        });

        /* 확인 버튼 클릭 시 실행 구문 */
        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SharedPreferences에서 토큰 가져오기
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                String token = sharedPreferences.getString("jwt_token", "");

                Call<RegisterDTO.RegisterResponse> call = service.register("Bearer " + token,
                        new RegisterDTO.RegisterRequest(
                                et_studentId.getText().toString(), et_password.getText().toString(),
                                et_name.getText().toString(), et_major.getText().toString(),
                                state, 생일));

                //LoginActivity로 이동
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        /* 취소 버튼 클릭 시 실행 구문 */
        Button btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterActivity.this, "회원가입을 취소합니다.", Toast.LENGTH_SHORT).show();
                RegisterActivity.super.onBackPressed(); //LoginActivity로 이동(뒤로가기)
            }
        });


    }

    /* 생년월일을 String(yyyyMMdd) -> LocalDate(yyyy-MM-dd)로 바꾸는 함수 */
    private LocalDate stringToDate(String dateString) {



    }
}