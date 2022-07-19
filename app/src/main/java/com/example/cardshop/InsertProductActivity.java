package com.example.cardshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class InsertProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_product);
        Objects.requireNonNull(getSupportActionBar()).hide(); //nasconde la barra superiore
    }

    public void goBack(View view) {
        startActivity(new Intent(this, HomeAdminActivity.class));
        finish();
    }

    public void addImage(View view) {

    }

    public void insertCard(View view) {

    }
}