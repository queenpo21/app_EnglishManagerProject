package com.example.app.model;
public class ExaminationDTO {
    private String idExam, name, format, examDate, idClass, idClassroom;

    public ExaminationDTO(String idExam, String name, String format,
                          String examDate, String idClass, String idClassroom) {
        this.idExam = idExam;
        this.name = name;
        this.format = format;
        this.examDate = examDate;
        this.idClass = idClass;
        this.idClassroom = idClassroom;
    }

    public String getIdExam() {
        return idExam;
    }

    public void setIdExam(String idExam) {
        this.idExam = idExam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getIdClass() {
        return idClass;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }

    public String getIdClassroom() {
        return idClassroom;
    }

    public void setIdClassroom(String idClassroom) {
        this.idClassroom = idClassroom;
    }

    @Override
    public String toString() {
        return "ExaminationDTO{" +
                "idExam='" + idExam + '\'' +
                ", name='" + name + '\'' +
                ", format='" + format + '\'' +
                ", examDate='" + examDate + '\'' +
                ", idClass='" + idClass + '\'' +
                ", idClassroom='" + idClassroom + '\'' +
                '}';
    }
}
