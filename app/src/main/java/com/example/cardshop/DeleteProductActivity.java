package com.example.cardshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class DeleteProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);
        Objects.requireNonNull(getSupportActionBar()).hide(); //nasconde la barra superiore
    }

    public void goBack(View view){
        startActivity(new Intent(this, HomeAdminActivity.class));
        finish();
    }


}