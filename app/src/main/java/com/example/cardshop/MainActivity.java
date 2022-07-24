package com.example.cardshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide(); //nasconde la barra superiore
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void Login(View view){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    public void Signin(View view){
        startActivity(new Intent(MainActivity.this, SigninActivity.class));
        finish();
    }
}