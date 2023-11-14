package com.example.se_app.service;

import com.example.se_app.dto.CalendarDTO;
import com.example.se_app.dto.LoginDTO;
import com.example.se_app.dto.MypageDTO;
import com.example.se_app.dto.NoticeDTO;
import com.example.se_app.dto.RegisterDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.Query;

public interface Service {

    //로그인
    @POST("/user/login")
    Call<LoginDTO.LoginResponse> login(@Body LoginDTO.LoginRequest loginRequest);

    //회원가입
    @POST("/user/signup")
    Call<RegisterDTO.RegisterResponse> register(@Body RegisterDTO.RegisterRequest registerRequest);

    //회원 정보 조회
    @GET("/member/mypage")
    Call<MypageDTO.MypageResponse> mypage(@Header("Authorization") String token);

    //회원 정보 수정
    @PATCH("/user/mypage")
    Call<MypageDTO.MypageResponse> edit(@Body RegisterDTO.RegisterRequest registerRequest);

    //공지사항 조회
    @GET("/notice/now")
    Call<NoticeDTO.NoticeResponse> notice(@Header("Authorization") String token);

    //목표시간 조회
    @GET("/studygoal")
    Call<CalendarDTO.GoalResponse> goal(@Header("Authorization") String token, @Query("month") String month);

    //출석기록 조회
    @GET("/myrecord")
    Call<CalendarDTO.TimeResponse> time(@Header("Authorization") String token, @Query("month") String month);

}
