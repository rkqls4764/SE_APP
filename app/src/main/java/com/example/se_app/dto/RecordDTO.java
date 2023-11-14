package com.example.se_app.dto;

public class RecordDTO {
    /* 랭킹보기 */
    public static class Rank {
        private String memberId;
        private String memberName;
        private String memberMajor;
        private int recordTime;

        //setter
        public String getMemberId(String memberId) { return memberId; }
        public String getMemberName(String memberName) { return memberName; }
        public String getMemberMajor(String memberId) { return memberMajor; }
        public int getRecordTime(int recordTime) { return recordTime; }

        //getter
        public void setMemberId() { this.memberId = memberId; }
        public void setMemberName() { this.memberName = memberName; }
        public void setMemberMajor() { this.memberMajor = memberMajor; }
        public void setRecordTime() { this.recordTime = recordTime; }
    }

    /* 당일 기록 조회 */
    public static class record {
        private int recordTime; //당일 기록시간

        //setter
        public void setRecordTime(int recordTime) {this.recordTime = recordTime; }

        //getter
        public int getRecordTimeToday() { return recordTime; }

    }

    /* 위치 요청 */
    public static class locationRequest {
        private Double userLatitude; //경도
        private Double userLongitude; //위도

        public locationRequest(Double userLatitude, Double userLongitude) {
            this.userLatitude = userLatitude;
            this.userLongitude = userLongitude;
        }

        //setter
        public void setUserLongitude(Double userLongitude) { this.userLatitude = userLongitude; }
        public void setUserLatitude(Double userLatitude) { this.userLatitude = userLatitude; }
    }

    /* 위치 응답 */
    public static class locationResponse {
        private String message; //매세지

        //getter
        public String getMessage() { return message; }
    }
}