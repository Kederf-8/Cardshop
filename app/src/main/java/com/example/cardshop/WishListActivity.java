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
import android.widget.ListView;
import android.widget.Toast;

import com.example.cardshop.adapters.CustomerCardAdapter;
import com.example.cardshop.model.CardModel;
import com.example.cardshop.model.CustomerModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class WishListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private boolean statusSideBar;
    private HashMap<String, String> user;
    private ArrayList<CardModel> wishlist;
    private CustomerModel customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        Objects.requireNonNull(getSupportActionBar()).hide(); //nasconde la barra superiore
        drawerLayout = findViewById(R.id.side_navigation_menu_customer);
        statusSideBar = false;
        setNavigationViewListener();
        //user = (HashMap<String, String>) getIntent().getSerializableExtra("user");
        //wishlist = (ArrayList<CardModel>) getIntent().getSerializableExtra("wishlist");
        //customer = new CustomerModel(user.get("UID"), user.get("email"), user.get("password"), wishlist);
        //FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        wishlist = new ArrayList<>();

        //Debug card example:
        //CardModel carta = new CardModel("id", "Athena", "20€", "SR05-EN013", "immaginiCarte/athena.jpg", 2.5, "Yu-gi-oh!");
        //CardModel carta2 = new CardModel("id", "Raikou", "80€", "SL9", "immaginiCarte/raikou.jpg", 3.5, "Pokemon");
        //wishlist.add(carta);
        //wishlist.add(carta2);

        setAdapter();
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
                startActivity(new Intent(this, LoginActivity.class));
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        statusSideBar = false;
        finish();
        return true;
    }

    public void setAdapter() {
        ListView listView = findViewById(R.id.item_list);
        CustomerCardAdapter adapter;
        if (!wishlist.isEmpty()) {
            adapter = new CustomerCardAdapter(this, R.layout.card_customer, wishlist);
            listView.setAdapter(adapter);
        } else
            Toast.makeText(WishListActivity.this, "Wishlist is empty!", Toast.LENGTH_SHORT).show();
    }
}