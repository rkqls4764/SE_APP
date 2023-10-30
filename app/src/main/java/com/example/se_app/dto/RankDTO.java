package com.example.se_app.dto;

public class RankDTO {
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