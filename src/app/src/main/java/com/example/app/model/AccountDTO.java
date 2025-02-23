package com.example.app.model;

public class AccountDTO {
    private String idAccount;
    private String idUser;
    private String userName;
    private String passWord;

    public AccountDTO(String idAccount, String idUser, String userName, String passWord) {
        this.idAccount = idAccount;
        this.idUser = idUser;
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "idAccount='" + idAccount + '\'' +
                ", idUser='" + idUser + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
