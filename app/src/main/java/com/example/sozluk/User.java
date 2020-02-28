package com.example.sozluk;

public class User {
    private String key;
    private int id;

    private String mail;
    private String password;

    public User(String key, int id,  String mail, String password) {
        this.key = key;
        this.id = id;

        this.mail = mail;
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
