package com.example.cardshop.model;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String UID;
    private String email;
    private String password;

    UserModel(String UID, String email, String password) {
        this.UID = UID;
        this.email = email;
        this.password = password;
    }
}