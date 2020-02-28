package com.example.sozluk;

import com.google.firebase.auth.FirebaseUser;

public  class Word2 {
    private String key;
    private String name;
    private String mean;
    private String addedBy;
    private Boolean isControlled;

    public Word2() {
    }

    public Word2(String key, String name, String mean, String addedBy, Boolean isControlled) {
        this.key = key;

        this.name = name;
        this.mean = mean;
        this.addedBy=addedBy;
        this.isControlled=isControlled;
    }

    public Boolean getControlled() {
        return isControlled;
    }

    public void setControlled(Boolean controlled) {
        isControlled = controlled;
    }

    public String getAdded_by() {
        return addedBy;
    }

    public void setAdded_by(String added_by) {
        this.addedBy = added_by;
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
}


