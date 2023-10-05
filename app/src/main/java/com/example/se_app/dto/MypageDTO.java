package com.example.se_app.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MypageDTO {
    /* 마이페이지 정보 조회 응답 */
    public static class MypageResponse {
        private String message; //오류 메세지
        private String userId; //학번(아이디)
        private String userPw; //비밀번호
        private String userName; //이름
        private String userMajor; //전공(학과)
        private String userState; //상태(휴학/재학/졸업)
        private LocalDate userBirth; //생년월일
        private LocalDateTime createTime; //가입일
        private LocalDateTime updateTime; //최종 수정 시간

        //getter
        public String getMessage() { return message; }
        public String getUserId() { return userId; }
        public String getUserPw() { return userPw; }
        public String getUserName() { return userName; }
        public String getUserMajor() { return userMajor; }
        public String getUserState() { return userState; }
        public LocalDate getUserBirth() { return userBirth; }
        public LocalDateTime getCreateTime() { return createTime; }
        public LocalDateTime getUpdateTime() { return updateTime; }
    }
}
