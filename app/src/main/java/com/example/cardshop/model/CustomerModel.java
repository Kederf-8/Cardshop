package com.example.cardshop.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomerModel extends UserModel implements Serializable {
    public static ArrayList<CardModel> wishlist;

    public CustomerModel(String UID, String email, String password, ArrayList<CardModel> wishlist) {
        super(UID, email, password);
        CustomerModel.wishlist = wishlist;
    }

    public static ArrayList<CardModel> getWishlist() {
        return wishlist;
    }

    public static void addToTheWishlist(CardModel card){
        wishlist.add(card);
    }

    public static void removeFromWishlist(CardModel card){
        wishlist.remove(card);
    }
}