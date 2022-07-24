package com.example.cardshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cardshop.model.CardModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    static boolean admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide(); //nasconde la barra superiore
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
    }

    public void Login(View view) {
        String email = ((EditText) findViewById(R.id.editText_LoginEmail)).getText().toString().toLowerCase();
        String password = ((EditText) findViewById(R.id.editText_LoginPassword)).getText().toString().toLowerCase();
        int checkValidation = ValidationInputLogin(email, password);
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
        } else if (checkValidation > 0) {
            Firebase.Login(email, password, this);
        }
    }

    private int ValidationInputLogin(String email, String password) {
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
        if (!domain.equals("gmail.com") && !domain.equals("outlook.it") && !domain.equals("outlook.com") && !domain.equals("icloud.com") && !domain.equals("yahoo.com") && !domain.equals("yahoo.it") && !domain.equals("live.com") && !domain.equals("hotmail.com") && !domain.equals("hotmail.it") && !domain.equals("libero.it")) {
            //incorrect domain
            return -8;
        }
        return 1;
    }

    public void CallbackLogin(int operationState, HashMap<String, Object> user, ArrayList<CardModel> wishlist, String UID) {
        String toast;
        if (operationState == 1) {
            toast = "Login Succesfull";
            Toast.makeText(this.getApplicationContext(), toast,
                    Toast.LENGTH_SHORT).show();
            Intent intent;
            if (UID.equals("XXPl2AXSpXQ9TyPcpSZnrkXB7Ie2")) {
                admin = true;
                intent = new Intent(LoginActivity.this, HomeAdminActivity.class);
                intent.putExtra("user", user);
            } else {
                admin = false;
                intent = new Intent(LoginActivity.this, HomeCustomerActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("wishlist", wishlist);
            }
            startActivity(intent);
            finish();
        } else if (operationState == -1) {
            toast = "User doesn't exist | the email wasn't verified | the password is incorrect";
            Toast.makeText(this.getApplicationContext(), toast,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void GetDataUser(String UID) {
        Firebase.GetDataUser(UID, this);
    }

    public void GoToSignin(View view) {
        startActivity(new Intent(LoginActivity.this, SigninActivity.class));
        finish();
        System.out.println("pressed");
    }

    public void goBack(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public static boolean getAdmin() {
        return admin;
    }
}