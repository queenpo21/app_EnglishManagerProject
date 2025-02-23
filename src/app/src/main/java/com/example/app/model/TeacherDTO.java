package com.example.app.model;

public class TeacherDTO {
    private String idTeacher;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String gender;
    private int salary;
    private String birthday;

    public TeacherDTO(String id, String fullName, String address, String phoneNumber, String gender, String birthday, int salary) {
        this.idTeacher = id;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.salary = salary;
        this.birthday = birthday;
    }

    public String getIdTeacher() {
        return idTeacher;
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

    public int getSalary() {
        return salary;
    }

    public void setIdTeacher(String idTeacher) {
        this.idTeacher = idTeacher;
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

    public void setSalary(int salary) {
        this.salary = salary;
    }


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "TeacherDTO{" +
                "idTeacher='" + idTeacher + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", salary=" + salary +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
