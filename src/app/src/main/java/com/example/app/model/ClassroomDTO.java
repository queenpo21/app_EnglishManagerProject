package com.example.app.model;

public class ClassroomDTO {
    private String idRoom;
    private String name;


    public ClassroomDTO(String idRoom, String name) {
        this.idRoom = idRoom;
        this.name = name;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public String getName() {
        return name;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassroomDTO{" +
                "idRoom='" + idRoom + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
