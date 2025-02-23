package com.example.app.model;

public class OfficialStudentDTO {
    private String idStudent;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String gender;
    private String birthday;
    private int status;
    public OfficialStudentDTO(String idStudent, String fullName,
                              String address, String phoneNumber, String gender,
                              String birthday, int status) {
        this.idStudent = idStudent;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthday = birthday;
        this.status = status;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public int getStatus() {
        return status;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "OfficialStudentDTO{" +
                "idStudent='" + idStudent + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", status=" + status +
                '}';
    }
}
