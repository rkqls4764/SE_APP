package com.example.se_app.service;

import com.example.se_app.dto.LoginDTO;
import com.example.se_app.dto.MypageDTO;
import com.example.se_app.dto.RankDTO;
import com.example.se_app.dto.RecordDTO;
import com.example.se_app.dto.RegisterDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface Service {

    //로그인
    @POST("/user/login")
    Call<LoginDTO.LoginResponse> login(@Body LoginDTO.LoginRequest loginRequest);

    //회원가입
    @POST("/user/signup")
    Call<RegisterDTO.RegisterResponse> register(@Body RegisterDTO.RegisterRequest registerRequest);

    //회원 정보 조회
    @GET("/user/mypage")
    Call<MypageDTO.MypageResponse> mypage(@Header("Authorization") String token);

    //회원 정보 수정
    @PATCH("/user/mypage")
    Call<MypageDTO.MypageResponse> edit(@Body RegisterDTO.RegisterRequest registerRequest);


    //랭킹 보기(출석 일수)
    @GET("/rank/day/{month}")
    Call<RankDTO.RankResponse> dayrank();

    //랭킹 보기(출석 시간)
    @GET("/rank/time/{month}")
    Call<RankDTO.RankResponse> timerank();


    //jwt 사용
    //당일 기록 가져오기
    @GET("/record/today")
    Call<RecordDTO.TodayRecord> todayrecord(@Header("Authorization") String token);

    //기록하기
    @POST("/record")
    Call<RecordDTO.StartRecord> startrecord(@Header("Authorization") String token, @Body RecordDTO.StartRecord startRecord);

    //기록 중단
    @POST("/record/stop")
    Call<RecordDTO.StopRecord> stoprecord(@Header("Authorization") String token, @Body RecordDTO.StopRecord stopRecord);

    //위치 보내기
    @POST("/record/location")
    Call<RecordDTO.Location> location(@Header("Authorization") String token, @Body RecordDTO.Location location);
}
