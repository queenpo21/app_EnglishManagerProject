package com.example.app.model;

public class ExamScoreDTO {
    private String idExamScore, idExam, idStudent, speaking, writing, listening, reading;

    public ExamScoreDTO(String idExamScore, String idExam, String idStudent,
                        String speaking, String writing, String listening, String reading) {
        this.idExamScore = idExamScore;
        this.idExam = idExam;
        this.idStudent = idStudent;
        this.speaking = speaking;
        this.writing = writing;
        this.listening = listening;
        this.reading = reading;
    }

    public String getIdExamScore() {
        return idExamScore;
    }

    public void setIdExamScore(String idExamScore) {
        this.idExamScore = idExamScore;
    }

    public String getIdExam() {
        return idExam;
    }

    public void setIdExam(String idExam) {
        this.idExam = idExam;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getSpeaking() {
        return speaking;
    }

    public void setSpeaking(String speaking) {
        this.speaking = speaking;
    }

    public String getWriting() {
        return writing;
    }

    public void setWriting(String writing) {
        this.writing = writing;
    }

    public String getListening() {
        return listening;
    }

    public void setListening(String listening) {
        this.listening = listening;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    @Override
    public String toString() {
        return "ExamScoreDTO{" +
                "idExamScore='" + idExamScore + '\'' +
                ", idExam='" + idExam + '\'' +
                ", idStudent='" + idStudent + '\'' +
                ", speaking='" + speaking + '\'' +
                ", writing='" + writing + '\'' +
                ", listening='" + listening + '\'' +
                ", reading='" + reading + '\'' +
                '}';
    }
}
