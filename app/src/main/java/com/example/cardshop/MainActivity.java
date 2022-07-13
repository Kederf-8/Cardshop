package com.example.cardshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //nasconde la barra superiore
        setContentView(R.layout.activity_main);
    }

    public void Login(View view){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void Signin(View view){
        Intent intent = new Intent(MainActivity.this, SigninActivity.class);
        startActivity(intent);
        finish();
    }
}