package com.example.cardshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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
    private CardModel card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_product);
        Objects.requireNonNull(getSupportActionBar()).hide(); //nasconde la barra superiore
        firestore = FirebaseFirestore.getInstance();
        list = new LinkedList<>();
        cardFetch();
    }

    public void goBack(View view){
        startActivity(new Intent(this, HomeAdminActivity.class));
        finish();
    }

    public void cardFetch() {
        firestore.collection("cards")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            HashMap<String, Object> info = (HashMap<String, Object>) document.getData();
                            card = new CardModel((String) info.get("UID"), (String) info.get("name"), (String) info.get("price"), (String) info.get("description"), (String) info.get("image"), (Double) info.get("rating"), (String) info.get("game"));
                            list.add(card);
                        }
                        setAdapter();
                    } else {
                        System.out.println("Error getting documents: " + task.getException());
                    }
                });
    }

    public void setAdapter() {
        ListView listView = findViewById(R.id.itemListModify);
        if (!list.isEmpty()) {
            AdminModifyCardAdapter adapter = new AdminModifyCardAdapter(this, R.layout.card_admin_modify, list);
            listView.setAdapter(adapter);
        } else Toast.makeText(ModifyProductActivity.this, "Card list is empty!", Toast.LENGTH_SHORT).show();
    }

    public void goToEditActivity(View view){
        Intent intent = new Intent(ModifyProductActivity.this, EditFieldsActivity.class);
        intent.putExtra("Card", card);
        startActivity(intent);
        finish();
    }
}