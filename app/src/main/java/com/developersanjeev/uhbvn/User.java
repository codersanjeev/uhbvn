package com.developersanjeev.uhbvn;

import androidx.annotation.Keep;

@Keep
public class User {
    private String name;
    private String email;
    private String uid;

    // required for firebase
    public User() {

    }

    public User(String name, String email, String uid) {
        this.name = name;
        this.email = email;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
