package com.example.se_app.dto;

import java.time.LocalDate;

public class RegisterDTO {
    /* 회원가입 요청 */
    public static class RegisterRequest {
        private String userId; //학번(아이디)
        private String userPw; //비밀번호
        private String userName; //이름
        private String userMajor; //전공(학과)
        private String userState; //상태(휴학/재학/졸업)
        private LocalDate userBirth; //생년월일

        public RegisterRequest(String userId, String userPw, String userName, String userMajor, String userState, LocalDate userBirth) {
            this.userId = userId;
            this.userPw = userPw;
            this.userName = userName;
            this.userMajor = userMajor;
            this.userState = userState;
            this.userBirth = userBirth;
        }

        //setter
        public void setUserId(String userId) { this.userId = userId; }
        public void setUserPw(String userPw) { this.userPw = userPw; }
        public void setUserName(String userName) { this.userName = userName; }
        public void setUserMajor(String userMajor) { this.userMajor = userMajor; }
        public void setUserState(String userState) { this.userState = userState; }
        public void setUserBirth(LocalDate userBirth) { this.userBirth = userBirth; }
    }

    /* 회원가입 응답 */
    public static class RegisterResponse {
        private String message;

        //getter
        public String getMessage() { return message; }
    }
}
