package com.example.app.model;

public class ClassDTO {
    private String classID, className, startDate, endDate, idProgram, idTeacher, idStaff, status;

    public ClassDTO(String classID, String className, String startDate,
                    String endDate, String idProgram, String idTeacher,
                    String idStaff, String status) {
        this.classID = classID;
        this.className = className;
        this.startDate = startDate;
        this.endDate = endDate;
        this.idProgram = idProgram;
        this.idTeacher = idTeacher;
        this.idStaff = idStaff;
        this.status = status;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(String idProgram) {
        this.idProgram = idProgram;
    }

    public String getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(String idTeacher) {
        this.idTeacher = idTeacher;
    }

    public String getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(String idStaff) {
        this.idStaff = idStaff;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassDTO{" +
                "classID='" + classID + '\'' +
                ", className='" + className + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", idProgram='" + idProgram + '\'' +
                ", idTeacher='" + idTeacher + '\'' +
                ", idStaff='" + idStaff + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
