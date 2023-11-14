package com.example.se_app.dto;

public class RecordDTO {
    /*출석 시간별 랭킹보기*/
    public static class TimeRank {
        private String memberId;
        private String memberName;
        private String memberMajor;
        private int recordTime;

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }
        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }
        public void setMemberMajor(String memberMajor) {
            this.memberMajor = memberMajor;
        }
        public void setRecordTime(int recordTime) {
            this.recordTime = recordTime;
        }
    }

    /*출석 날짜별 랭킹보기*/
    public static class DayRank {
        private String memberId;
        private String memberName;
        private String memberMajor;
        private int recordTime;
        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }
        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }
        public void setMemberMajor(String memberMajor) {
            this.memberMajor = memberMajor;
        }
        public void setRecordTime(int recordTime) {
            this.recordTime = recordTime;
        }
    }

    /*기록하기*/
    public static class Record {
        private Double userLatitude;
        private Double userLongitude;
        private int recordTime;
        public void todayRecord(int recordTime) {
            this.recordTime = recordTime;
        }
        public void setUserLatitude(Double userLatitude) {this.userLatitude = userLatitude; }
        public void setUserLongitude(Double userLongitude) {this.userLongitude = userLongitude; }
    }
}