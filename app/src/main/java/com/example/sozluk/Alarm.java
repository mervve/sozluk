package com.example.sozluk;

import com.google.firebase.auth.FirebaseUser;

public class Alarm {
    private int hour;
    private int min;
    private String  user;
    private String pushKey;

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }

    public Alarm(int hour, int min, String user, String pushKey) {
        this.hour = hour;
        this.min = min;
        this.user = user;
        this.pushKey= pushKey;
    }

    public Alarm() {
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String  getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
