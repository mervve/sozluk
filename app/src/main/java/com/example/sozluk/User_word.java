package com.example.sozluk;

import java.sql.Timestamp;
import java.util.Date;

public class User_word {
    private String id;
    private String key;
    private String name;
    private String mean;
    private Long date;
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public User_word() { }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public User_word(String id, String key, String name, String mean, Long date, int level) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.mean = mean;
        this.date=date;
        this.level=level;
    }
}
