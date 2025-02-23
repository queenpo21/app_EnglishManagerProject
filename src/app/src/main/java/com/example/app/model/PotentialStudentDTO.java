package com.example.app.model;

public class PotentialStudentDTO {
    private String studentID, studentName, phoneNumber, gender, address, level, appointmentNumber;

    public PotentialStudentDTO(String studentID, String studentName,
                               String phoneNumber, String gender, String address,
                               String level, String appointmentNumber) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.address = address;
        this.level = level;
        this.appointmentNumber = appointmentNumber;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public String getLevel() {
        return level;
    }



    @Override
    public String toString() {
        return "PotentialStudentDTO{" +
                "studentID='" + studentID + '\'' +
                "studentName='" + studentName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", level='" + level + '\'' +
                ", appointmentNumber='" + appointmentNumber + '\'' +
                '}';
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }
}
