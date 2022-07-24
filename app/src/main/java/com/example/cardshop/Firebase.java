package com.example.cardshop;

import com.example.cardshop.model.CardModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Firebase {
    public static void Login(String email, String password, LoginActivity activity) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user.isEmailVerified()) {
                    activity.GetDataUser(user.getUid());
                } else {
                    activity.CallbackLogin(-1, null, null, null);
                }
            } else {
                activity.CallbackLogin(-1, null, null, null);
            }
        });
    }

    public static void Register(String email, String password, SigninActivity activity) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                user.sendEmailVerification();
                String UID = user.getUid();
                activity.CallbackRegister(email, password, UID);
                //updateUI(user);
            } else {
                System.out.println("user problem");
                activity.CallbackRegister(-1);
            }
        });
    }

    public static void GetDataUser(String ID, LoginActivity activity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference doc = db.collection("Users").document(ID);
        doc.get().addOnSuccessListener(documentSnapshot -> {
            HashMap<String, Object> user = new HashMap<>();
            user.put("UID", documentSnapshot.getId());
            user.put("email", documentSnapshot.getString("email"));
            user.put("password", documentSnapshot.getString("password"));
            ArrayList<CardModel> wishlist = new ArrayList<>();
            ArrayList<HashMap<String, Object>> listFirebase = (ArrayList<HashMap<String, Object>>) documentSnapshot.getData().get("wishlist");
            for (HashMap<String, Object> product : listFirebase) {
                String uid = product.get("UID").toString();
                String name = product.get("name").toString();
                String image = product.get("image").toString();
                String price = product.get("price").toString();
                String description = product.get("description").toString();
                String game = product.get("game").toString();
                Double rating = Double.parseDouble(product.get("rating").toString());
                wishlist.add(new CardModel(uid, name, price, description, image, rating, game));
            }
            activity.CallbackLogin(1, user, wishlist, ID);
        }).addOnFailureListener(e -> activity.CallbackLogin(-1, null, null, null));
    }

    public static void SaveDataUser(String collection, String UID, Map<String, Object> object, SigninActivity activity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference doc = db.collection(collection).document(UID);
        doc.set(object).addOnSuccessListener(unused -> {
            System.out.println("User altready registered");
            activity.CallbackRegister(1);
        }).addOnFailureListener(e -> {
            System.out.println("User registration failed");
            activity.CallbackRegister(-1);
        });
    }
}
