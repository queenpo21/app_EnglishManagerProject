package com.example.app.model;

public class ClassCollectingFees {
    String idClass, className, numStudent, totalMoney;

    public ClassCollectingFees(String idClass, String className, String numStudent, String totalMoney) {
        this.idClass = idClass;
        this.className = className;
        this.numStudent = numStudent;
        this.totalMoney = totalMoney;
    }

    public String getIdClass() {
        return idClass;
    }

    public String getClassName() {
        return className;
    }

    public String getNumStudent() {
        return numStudent;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setNumStudent(String numStudent) {
        this.numStudent = numStudent;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }
}
