package com.example.cardshop.model;

import android.annotation.SuppressLint;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel extends UserModel implements Serializable {
    private static List<CardModel> wishlist;
    private static DocumentReference user;
    private static String email;

    @SuppressLint("StaticFieldLeak")
    private static FirebaseFirestore firestore;

    public CustomerModel(String UID, String email, String password, List<CardModel> wishlist) {
        super(UID, email, password);
        CustomerModel.wishlist = wishlist;
        firestore = FirebaseFirestore.getInstance();
    }

    public static List<CardModel> getWishlist() {
        return wishlist;
    }

    public static void addToTheWishlist(CardModel card) {
        if (!wishlist.contains(card)) {
            wishlist.add(card);
            user = firestore.collection("Users").document(email);
            user.update("wishlist", FieldValue.arrayUnion(card.getName()));
        } else {
            System.out.println("Non si aggiunge l'elemento, già è nella lista preferiti");
        }
    }

    public static void removeFromWishlist(CardModel card) {
        wishlist.remove(card);
    }
}