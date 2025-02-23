package com.example.app.model;

public class CollectionTuitionFeesDTO {

   private String idBill, idStudent, collectionDate, money;

    public CollectionTuitionFeesDTO(String idBill, String idStudent,
                                    String collectionDate, String money) {
        this.idBill = idBill;
        this.idStudent = idStudent;
        this.collectionDate = collectionDate;
        this.money = money;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate = collectionDate;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Collection_Tuition_Fees{" +
                "idBill='" + idBill + '\'' +
                ", idStudent='" + idStudent + '\'' +
                ", collectionDate='" + collectionDate + '\'' +
                ", money='" + money + '\'' +
                '}';
    }
}
