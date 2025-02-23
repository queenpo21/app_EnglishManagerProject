package com.example.app.model;

public class RevenueReportByYear {
    private int index, month;
    private String idClass, nameClass, nameProgram;
    private int tuition, numberOfStudents, revenue;


    public RevenueReportByYear(int index, int month, String idClass, String nameClass,
                               String nameProgram, int tuition, int numberOfStudents, int revenue) {
        this.index = index;
        this.month = month;
        this.idClass = idClass;
        this.nameClass = nameClass;
        this.nameProgram = nameProgram;
        this.tuition = tuition;
        this.numberOfStudents = numberOfStudents;
        this.revenue = revenue;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getIdClass() {
        return idClass;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public String getNameProgram() {
        return nameProgram;
    }

    public void setNameProgram(String nameProgram) {
        this.nameProgram = nameProgram;
    }

    public int getTuition() {
        return tuition;
    }

    public void setTuition(int tuition) {
        this.tuition = tuition;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    @Override
    public String toString() {
        return "RevenueReportByYear{" +
                "index=" + index +
                ", month=" + month +
                ", idClass='" + idClass + '\'' +
                ", nameClass='" + nameClass + '\'' +
                ", nameProgram='" + nameProgram + '\'' +
                ", tuition=" + tuition +
                ", numberOfStudents=" + numberOfStudents +
                ", revenue=" + revenue +
                '}';
    }
}
