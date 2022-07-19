package com.example.cardshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class HomeAdminActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        Objects.requireNonNull(getSupportActionBar()).hide(); //nasconde la barra superiore
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void goToInsertCard(View view){
        startActivity(new Intent(this, InsertProductActivity.class));
    }

    public void goToRemoveCard(View view){
        startActivity(new Intent(this, DeleteProductActivity.class));
    }

    public void goToUpdateCard(View view){
        startActivity(new Intent(this, ModifyProductActivity.class));
    }

    public void logOut(View view){
        firebaseAuth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}