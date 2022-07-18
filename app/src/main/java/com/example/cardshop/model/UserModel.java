package com.example.cardshop.model;

import java.io.Serializable;

public class UserModel implements Serializable {
    public String UID;
    public String password;
    public String email;

    UserModel(String UID, String password, String email) {
        this.UID = UID;
        this.password = password;
        this.email = email;
    }
}