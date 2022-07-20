package com.example.cardshop;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InsertProductActivity extends AppCompatActivity {

    private static FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_product);
        Objects.requireNonNull(getSupportActionBar()).hide(); //nasconde la barra superiore
        firestore = FirebaseFirestore.getInstance();
    }

    public void goBack(View view) {
        startActivity(new Intent(this, HomeAdminActivity.class));
        finish();
    }

    public void addImage(View view) {
        //TODO
    }

    public void insertCard(View view) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("name", ((EditText) findViewById(R.id.editTextName)).getText().toString());
        docData.put("description", ((EditText) findViewById(R.id.editTextDescription)).getText().toString());
        docData.put("price", ((EditText) findViewById(R.id.editTextPrice)).getText().toString());
        docData.put("game", ((EditText) findViewById(R.id.editTextGame)).getText().toString());

        firestore.collection("cards")
                .add(docData)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));
    }
}