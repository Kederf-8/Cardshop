package com.example.cardshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cardshop.model.CardModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SigninActivity extends AppCompatActivity {
    Boolean stopUserInteractions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Objects.requireNonNull(getSupportActionBar()).hide(); //nasconde la barra superiore
        stopUserInteractions = false;
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (stopUserInteractions) {
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    public void Signin(View view) {
        String email = ((EditText) findViewById(R.id.editText_SigninEmail)).getText().toString().toLowerCase();
        String password = ((EditText) findViewById(R.id.editText_SigninPassword)).getText().toString().toLowerCase();

        int checkValidation = ValidationInputRegister(email, password);
        if (checkValidation < 0) {
            String toast;
            if (checkValidation == -1) {
                toast = "Email field is empty";
                Toast.makeText(this.getApplicationContext(), toast,
                        Toast.LENGTH_SHORT).show();
            } else if (checkValidation == -2) {
                toast = "Password field is empty";
                Toast.makeText(this.getApplicationContext(), toast,
                        Toast.LENGTH_SHORT).show();
            } else if (checkValidation == -3) {
                toast = "Email is too short";
                Toast.makeText(this.getApplicationContext(), toast,
                        Toast.LENGTH_SHORT).show();
            } else if (checkValidation == -4) {
                toast = "Email is too long";
                Toast.makeText(this.getApplicationContext(), toast,
                        Toast.LENGTH_SHORT).show();
            } else if (checkValidation == -5) {
                toast = "Password is too short";
                Toast.makeText(this.getApplicationContext(), toast,
                        Toast.LENGTH_SHORT).show();
            } else if (checkValidation == -6) {
                toast = "Password is too long";
                Toast.makeText(this.getApplicationContext(), toast,
                        Toast.LENGTH_SHORT).show();
            } else if (checkValidation == -7) {
                toast = "Incorrect email format";
                Toast.makeText(this.getApplicationContext(), toast,
                        Toast.LENGTH_SHORT).show();
            } else if (checkValidation == -8) {
                toast = "Incorrect domain in email field";
                Toast.makeText(this.getApplicationContext(), toast,
                        Toast.LENGTH_SHORT).show();
            }
        }
        stopUserInteractions = true;
        Firebase.Register(email, password, this);
    }

    public void CallbackRegister(int operationState) {
        String toast;
        if (operationState == 1) {
            Intent intent = new Intent(new Intent(SigninActivity.this, LoginActivity.class));
            String email = ((EditText) findViewById(R.id.editText_SigninEmail)).getText().toString().toLowerCase();
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        } else if (operationState == -1) {
            stopUserInteractions = false;
            toast = "User already exist";
            Toast.makeText(this.getApplicationContext(), toast,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void CallbackRegister(String email, String password, String UID) {
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("password", password);
        ArrayList<CardModel> wishlist = new ArrayList<>();
        //wishlist.add(new CardModel("1312412", "123123", "asfasfas", "fasfasf", "fasfasf", 0.0, 0, "fasfa", 0.0, 0));
        //wishlist.add(new CardModel("1312412", "123123", "asfasfas", "fasfasf", "fasfasf", 0.0, 0, "fasfa", 0.0, 0));
        //wishlist.add(new CardModel("1312412", "123123", "asfasfas", "fasfasf", "fasfasf", 0.0, 0, "fasfa", 0.0, 0));
        user.put("wishlist", wishlist);
        Firebase.SaveDataUser("Users", UID, user, this);
    }

    public void GoToLogin(View view) {
        startActivity(new Intent(SigninActivity.this, LoginActivity.class));
        finish();
        System.out.println("pressed");
    }

    private int ValidationInputRegister(String email, String password) {
        if (email.isEmpty()) {  //email is empty
            return -1;
        }
        if (password.isEmpty()) { //password is empty
            return -2;
        }
        if (email.length() < 10) { //email is too short
            return -3;
        }
        if (email.length() > 50) { //email is too long
            return -4;
        }
        if (password.length() < 8) { //password is too short
            return -5;
        }
        if (password.length() > 16) { //password is too long
            return -6;
        }
        int at = email.indexOf("@");
        if (at == -1) { //is not email
            return -7;
        }
        String domain = email.substring(at + 1);
        System.out.println(domain);
        if (!domain.equals("gmail.com") && !domain.equals("outlook.it") && !domain.equals("outlook.com") && !domain.equals("icloud.com") && !domain.equals("yahoo.com") && !domain.equals("yahoo.it") && !domain.equals("live.com") && !domain.equals("hotmail.com") && !domain.equals("hotmail.it")) {
            //incorrect domain
            return -8;
        }
        return 1;
    }
}