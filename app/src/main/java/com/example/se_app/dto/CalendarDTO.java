package com.example.se_app.dto;

import java.time.LocalDate;

public class CalendarDTO {
    String month; //선택한 날짜의 월

    /* 목표시간 응답 */
    public static class GoalResponse {
        int studyGoal; //목표시간(초)

        //getter
        public int getStudyGoal() { return studyGoal; }
    }

    /* 출석 기록 응답 */
    public static class TimeResponse {
        int recordTime; //출석시간(초)
        LocalDate recordDate; //날짜

        //getter
        public int getRecordTime() { return recordTime; }
        public LocalDate getRecordDate() { return recordDate; }
    }

}
