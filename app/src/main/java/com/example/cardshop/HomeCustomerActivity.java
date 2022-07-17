package com.example.cardshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.cardshop.model.CardModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class HomeCustomerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private boolean statusSideBar;
    HashMap<String, String> user;
    ArrayList<CardModel> wishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide(); //nasconde la barra superiore
        setContentView(R.layout.activity_home_customer);
        drawerLayout = findViewById(R.id.side_navigation_menu_customer);
        statusSideBar = false;
        user = (HashMap<String, String>) getIntent().getSerializableExtra("user");
        wishlist = (ArrayList<CardModel>) getIntent().getSerializableExtra("wishlist");
        setNavigationViewListener();
    }

    public void openSideBarMenu(View view) {
        if (!statusSideBar) {
            drawerLayout.openDrawer(GravityCompat.START);
            statusSideBar = true;
        } else {
            drawerLayout.closeDrawer(GravityCompat.START);
            statusSideBar = false;
        }
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = findViewById(R.id.navigation_view_menu_customer);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Home_Cutstomer_Button_Side_Menu: {
                Intent refresh = new Intent(this, HomeCustomerActivity.class);
                refresh.putExtra("user", user);
                refresh.putExtra("wishlist", wishlist);
                startActivity(refresh);
                break;
            }
            case R.id.Wishlist_Customer_Button_Side_Menu: {
                Intent refresh = new Intent(this, WishListActivity.class);
                refresh.putExtra("user", user);
                refresh.putExtra("wishlist", wishlist);
                startActivity(refresh);
                break;
            }
            case R.id.Customer_Logout_Button_Side_Menu: {
                startActivity(new Intent(HomeCustomerActivity.this, LoginActivity.class));
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        statusSideBar = false;
        finish();
        return true;
    }
}