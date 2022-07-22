package com.example.cardshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.cardshop.adapters.AdminDeleteCardAdapter;
import com.example.cardshop.adapters.AdminModifyCardAdapter;
import com.example.cardshop.model.CardModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ModifyProductActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private List<CardModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_product);
        Objects.requireNonNull(getSupportActionBar()).hide(); //nasconde la barra superiore
        firestore = FirebaseFirestore.getInstance();
        list = new LinkedList<>();
        cardFetch(list);
    }

    public void goBack(View view){
        startActivity(new Intent(this, HomeAdminActivity.class));
        finish();
    }

    public void cardFetch(List<CardModel> list) {
        firestore.collection("cards")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            HashMap<String, Object> info = (HashMap<String, Object>) document.getData();
                            CardModel card = new CardModel((String) info.get("UID"), (String) info.get("name"), (String) info.get("price"), (String) info.get("description"), (String) info.get("image"), (Double) info.get("rating"), (String) info.get("game"));
                            list.add(card);
                        }
                        setAdapter(list);
                    } else {
                        System.out.println("Error getting documents: " + task.getException());
                    }
                });
    }

    public void setAdapter(List<CardModel> cards) {
        ListView listView = findViewById(R.id.itemListModify);
        AdminModifyCardAdapter adapter = new AdminModifyCardAdapter(this, R.layout.card_admin_modify, cards);
        listView.setAdapter(adapter);
    }
}