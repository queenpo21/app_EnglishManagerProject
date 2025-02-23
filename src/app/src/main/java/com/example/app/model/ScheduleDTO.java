package com.example.app.model;

public class ScheduleDTO {
    String idSchedule, dayOfWeek, startTime, endTime, idClass, idClassroom;
    public ScheduleDTO(String idSchedule, String dayOfWeek, String startTime,
                       String endTime, String idClass, String idClassroom) {
        this.idSchedule = idSchedule;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.idClass = idClass;
        this.idClassroom = idClassroom;
    }
    public String getIdSchedule() {
        return idSchedule;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getIdClass() {
        return idClass;
    }
    public String getIdClassroom() {
        return idClassroom;
    }

    public void setIdSchedule(String idSchedule) {
        this.idSchedule = idSchedule;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }
    public void setIdClassroom(String idClassroom) {
        this.idClassroom = idClassroom;
    }

    @Override
    public String toString() {
        return "ScheduleDTO{" +
                "idSchedule='" + idSchedule + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", idClass='" + idClass + '\'' +
                ", idClassroom='" + idClassroom + '\'' +
                '}';
    }
}
