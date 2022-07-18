package com.example.cardshop.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomerModel extends UserModel implements Serializable {
    public ArrayList<CardModel> wishlist;

    public CustomerModel(String UID, String password, String email, ArrayList<CardModel> wishlist) {
        super(UID, password, email);
        this.wishlist = wishlist;
    }
}