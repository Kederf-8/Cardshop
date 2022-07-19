package com.example.cardshop.model;

import java.io.Serializable;

public class AdminModel extends UserModel implements Serializable {

    public AdminModel(String UID, String password, String email) {
        super(UID, password, email);
    }
}
