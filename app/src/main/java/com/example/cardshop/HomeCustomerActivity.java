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

import com.example.cardshop.adapters.CustomerCardAdapter;
import com.example.cardshop.model.CardModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class HomeCustomerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private boolean statusSideBar;
    HashMap<String, String> user;
    ArrayList<CardModel> wishlist;
    FirebaseFirestore firestore;
    List<CardModel> list;

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
        firestore = FirebaseFirestore.getInstance();
        list = new LinkedList<>();
        cardFetch(list);
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

    public void cardFetch(List<CardModel> list) {
        //si Scaricano tutti i prodotti
        firestore.collection("cards")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            HashMap<String, Object> info = (HashMap<String, Object>) document.getData();
                            //Si creano i prodotti e si aggiungono ad una lista
                            CardModel card = new CardModel((String) info.get("UID"), (String) info.get("name"), (String) info.get("price"), (String) info.get("description"), (String) info.get("image"), (Double) info.get("rating"), (String) info.get("game"));
                            list.add(card);
                        }
                        //si richiama l'adapter
                        setAdapter(list);
                    } else {
                        System.out.println("Error getting documents: " + task.getException());
                    }
                });
    }

    public void setAdapter(List<CardModel> list) {
        ListView listView = (ListView) findViewById(R.id.item_list);
        // Si distingue tra chi è l'utente, se un utente base o l'admin
        if (LoginActivity.getAdmin()) {
            // Adapter admin
            CustomerCardAdapter adapter = new CustomerCardAdapter(this, R.layout.card_customer, list);
            listView.setAdapter(adapter);
        } else {
            // Adapter Client
            System.out.println("Adapter client");
            CustomerCardAdapter adapter = new CustomerCardAdapter(this, R.layout.card_customer, list);
            listView.setAdapter(adapter);
        }
    }
}