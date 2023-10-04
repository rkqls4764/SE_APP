package com.example.se_app.dto;

public class LoginDTO {
    /* 로그인 요청 */
    public static class LoginRequest {
        private String userId;
        private String userPw;

        public LoginRequest(String userId, String userPw) {
            this.userId = userId;
            this.userPw = userPw;
        }

        //setter
        public void setUserId(String userId) { this.userId = userId; }
        public void setUserPw(String userPw) { this.userPw = userPw; }
    }

    /* 로그인 응답 */
    public static class LoginResponse {
        private String token;

        //getter
        public String getToken() { return token; }
    }
}
