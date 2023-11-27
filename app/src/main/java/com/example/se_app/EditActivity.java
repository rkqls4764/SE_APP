package com.example.se_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se_app.dto.EditDTO;
import com.example.se_app.dto.MypageDTO;
import com.example.se_app.instance.RetrofitInstance;
import com.example.se_app.service.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    Service service = RetrofitInstance.getRetrofitInstance().create(Service.class);
    private String state;

    /* 화면 시작 시 실행 함수 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        EditText et_password = findViewById(R.id.et_password); //비밀번호
        EditText et_name = findViewById(R.id.et_name); //이름
        EditText et_major = findViewById(R.id.et_major); //학과
        EditText et_birth = findViewById(R.id.et_birth); //생년월일
        Spinner sp_state = findViewById(R.id.spinner); //상태(재학/휴학/졸업)

        /* 상태 리스트의 아이템 클릭 시 실행 구문 */
        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //아이템이 선택된 경우
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state = adapterView.getItemAtPosition(i).toString();
            }

            //아무것도 선택되지 않은 경우
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                state = "";
            }
        });

        //SharedPreferences에서 토큰 가져오기
        String token = getToken();

        //사용자 정보 가져오기
        getUserData(token, et_password, et_name, et_major, et_birth, sp_state);

        //확인 버튼 클릭 시 실행
        clickBtnOk(token, et_password, et_name, et_major, et_birth);

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
    void getUserData(String token, EditText et_password, EditText et_name, EditText et_major, EditText et_birth, Spinner sp_state) {
        Call<MypageDTO.MypageResponse> call = service.mypage("Bearer " + token);
        call.enqueue(new Callback<MypageDTO.MypageResponse>() {
            //서버와 통신 성공
            @Override
            public void onResponse(Call<MypageDTO.MypageResponse> call, Response<MypageDTO.MypageResponse> response) {
                //응답 성공(200): 데이터베이스에서 회원 정보를 제대로 읽어왔을 때
                if (response.isSuccessful()) {
                    //정보 출력
                    et_password.setText("비밀번호 : " + response.body().getMemberPw().toString());
                    et_name.setText("이름 : " + response.body().getMemberName().toString());
                    et_major.setText("학과 : " + response.body().getMemberMajor().toString());
                    et_birth.setText("생년월일 : " + response.body().getMemberBirth().toString());
                    String state = response.body().getMemberState().toString();
                    sp_state.setSelection(getIndex(sp_state, state));
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
    void clickBtnOk(String token, EditText et_password, EditText et_name, EditText et_major, EditText et_birth) {

        Button btn_ok = findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditDTO.EditRequest editRequest = new EditDTO.EditRequest(
                        et_password.getText().toString(),
                        et_name.getText().toString(), et_major.getText().toString(),
                        state, et_birth.getText().toString());

                Call<EditDTO.EditResponse> call = service.edit("Bearer " + token, editRequest);
                call.enqueue(new Callback<EditDTO.EditResponse>() {
                    //서버와 통신 성공
                    @Override
                    public void onResponse(Call<EditDTO.EditResponse> call, Response<EditDTO.EditResponse> response) {
                        //응답 성공(200): 데이터베이스에서 회원 정보를 제대로 읽어왔을 때
                        if (response.isSuccessful()) {
                            //body의 성공 메세지를 저장
                            String message = response.body().getMessage();

                            //성공 메세지를 토스트 메세지로 띄움
                            Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();

                            //MypageActivity로 이동
                            Intent intent = new Intent(EditActivity.this, MypageActivity.class);
                            startActivity(intent);
                        }
                    }

                    //서버와 통신 실패
                    @Override
                    public void onFailure(Call<EditDTO.EditResponse> call, Throwable t) {
                        Toast.makeText(EditActivity.this, "서버와 통신을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "서버 통신 실패: " + t.getMessage());
                    }
                });
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