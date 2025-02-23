package com.example.app.model;

public class TeachingDTO {
    /* "ID_TEACHING INTEGER PRIMARY KEY AUTOINCREMENT, " +
             "ID_STUDENT TEXT , " +
             "ID_CLASS TEXT, " +*/

    private String idTeaching, idStudent, idClass;

    public TeachingDTO(String idTeaching, String idStudent, String idClass) {
        this.idTeaching = idTeaching;
        this.idStudent = idStudent;
        this.idClass = idClass;
    }

    public String getIdTeaching() {
        return idTeaching;
    }

    public void setIdTeaching(String idTeaching) {
        this.idTeaching = idTeaching;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getIdClass() {
        return idClass;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }

    @Override
    public String toString() {
        return "TeachingDTO{" +
                "idTeaching='" + idTeaching + '\'' +
                ", idStudent='" + idStudent + '\'' +
                ", idClass='" + idClass + '\'' +
                '}';
    }
}
