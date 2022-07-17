package com.example.cardshop;

import androidx.annotation.NonNull;

import com.example.cardshop.model.CardModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Firebase {
    public static void Login(String email, String password, LoginActivity activity) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    String email = user.getEmail();
                    boolean emailVerified = user.isEmailVerified();
                    if (emailVerified) {
                        activity.GetDataUser(user.getUid().toString());
                    } else {
                        activity.CallbackLogin(-1, null, null, null);
                    }
                } else {
                    activity.CallbackLogin(-1, null, null, null);
                }
            }
        });
    }

    public static void Register(String email, String password, SigninActivity activity) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    user.sendEmailVerification();
                    String UID = user.getUid().toString();
                    activity.CallbackRegister(email, password, UID);
                    //updateUI(user);
                } else {
                    System.out.println("user problem");
                    activity.CallbackRegister(-1);
                }
            }
        });
    }

    public static void GetDataUser(String ID, LoginActivity activity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference doc = db.collection("Users").document(ID);
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                HashMap<String, Object> user = new HashMap<>();
                user.put("UID", documentSnapshot.getId().toString());
                user.put("name", documentSnapshot.getString("name"));
                user.put("surname", documentSnapshot.getString("surname"));
                user.put("email", documentSnapshot.getString("email"));
                user.put("password", documentSnapshot.getString("password"));
                ArrayList<CardModel> wishlist = new ArrayList<>();
                ArrayList<HashMap<String, Object>> listFirebase = (ArrayList<HashMap<String, Object>>) documentSnapshot.getData().get("wishlist");
                for (HashMap<String, Object> product : listFirebase) {
                    String id = product.get("UID").toString();
                    String name = product.get("name").toString();
                    String image = product.get("image").toString();
                    String price = product.get("price").toString();
                    String description = product.get("description").toString();
                    String game = product.get("game").toString();
                    Integer rating = Integer.parseInt(product.get("rating").toString());
                    wishlist.add(new CardModel(id, name, price, description, image, rating, game));
                }
                activity.CallbackLogin(1, user, wishlist, ID);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                activity.CallbackLogin(-1, null, null, null);
            }
        });
    }

    public static void SaveDataUser(String collection, String UID, Map<String, Object> object, SigninActivity activity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference doc = db.collection(collection).document(UID);
        doc.set(object).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                System.out.println("utente registrato");
                activity.CallbackRegister(1);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("probemi durante la registrazione dedll'utente");
                activity.CallbackRegister(-1);
            }
        });
    }
}
