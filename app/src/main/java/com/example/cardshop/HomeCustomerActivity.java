package com.example.cardshop;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.cardshop.model.CardModel;
import com.example.cardshop.model.CustomerModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class HomeCustomerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private boolean statusSideBar;
    HashMap<String, String> user;
    ArrayList<CardModel> wishlist;
    ArrayList<CardModel> allCards;
    ArrayList<CardModel> cardGamePokemon;
    ArrayList<CardModel> cardGameYugioh;
    ArrayList<CardModel> cardGameMagic;
    ArrayList<CardModel> cardGameDragonball;
    ArrayList<CardModel> cardGameDigimon;
    private CustomerModel customer;

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
                startActivity(new Intent(this, LoginActivity.class));
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        statusSideBar = false;
        finish();
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void initProducts(ArrayList<CardModel> allCards, ArrayList<CardModel> cardGamePokemon, ArrayList<CardModel> cardGameYugioh, ArrayList<CardModel> cardGameMagic, ArrayList<CardModel> cardGameDragonball, ArrayList<CardModel> cardGameDigimon) {
        this.allCards = allCards;
        this.cardGamePokemon = cardGamePokemon;
        this.cardGameYugioh = cardGameYugioh;
        this.cardGameMagic = cardGameMagic;
        this.cardGameDragonball = cardGameDragonball;
        this.cardGameDigimon = cardGameDigimon;

        allCards.forEach(productModel -> System.out.println("allCards: " + productModel.name));
        cardGamePokemon.forEach(productModel -> System.out.println("pokemon: " + productModel.name));
        cardGameYugioh.forEach(productModel -> System.out.println("utility: " + productModel.name));
        cardGameMagic.forEach(productModel -> System.out.println("photo: " + productModel.name));
        cardGameDragonball.forEach(productModel -> System.out.println("video: " + productModel.name));
        cardGameDigimon.forEach(productModel -> System.out.println("fiannce: " + productModel.name));
        //initList();
    }

    /*public void initList() {
        RecyclerView.Adapter adapter;
        TextView title;
        RecyclerView recyclerView;
        if (!allCards.isEmpty()) {
            recyclerView = findViewById(R.id.InEvidenceList);
            adapter = new CustomerCardAdapter(this, allCards, this, customer);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        } else {
            title = findViewById(R.id.TextView_InEvidenceList);
            title.setVisibility(View.GONE);
            recyclerView = findViewById(R.id.InEvidenceList);
            recyclerView.setVisibility(View.GONE);
        }

        if (!cardGamePokemon.isEmpty()) {
            recyclerView = findViewById(R.id.GameProducts);
            adapter = new CustomerCardAdapter(this, cardGamePokemon, this, customer);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        } else {
            title = findViewById(R.id.textView_GamesCatTitle);
            title.setVisibility(View.GONE);
            recyclerView = findViewById(R.id.GameProducts);
            recyclerView.setVisibility(View.GONE);
        }

        if (!cardGameYugioh.isEmpty()) {
            recyclerView = findViewById(R.id.UtilityProducts);
            adapter = new CustomerCardAdapter(this, cardGameYugioh, this, customer);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        } else {
            title = findViewById(R.id.textView_UtilityCatTitle);
            title.setVisibility(View.GONE);
            recyclerView = findViewById(R.id.UtilityProducts);
            recyclerView.setVisibility(View.GONE);
        }

        if (!cardGameMagic.isEmpty()) {
            recyclerView = findViewById(R.id.PhotoEditingProducts);
            adapter = new CustomerCardAdapter(this, cardGameMagic, this, customer);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        } else {
            title = findViewById(R.id.textView_PhotoEditingCatTitle);
            title.setVisibility(View.GONE);
            recyclerView = findViewById(R.id.PhotoEditingProducts);
            recyclerView.setVisibility(View.GONE);
        }

        if (!cardGameDragonball.isEmpty()) {
            recyclerView = findViewById(R.id.VideoMakingProducts);
            adapter = new CustomerCardAdapter(this, cardGameDragonball, this, customer);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        } else {
            title = findViewById(R.id.textView_VideoMakingCatTitle);
            title.setVisibility(View.GONE);
            recyclerView = findViewById(R.id.VideoMakingProducts);
            recyclerView.setVisibility(View.GONE);
        }

        if (!cardGameDigimon.isEmpty()) {
            recyclerView = findViewById(R.id.FinanceProducts);
            adapter = new CustomerCardAdapter(this, cardGameDigimon, this, customer);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        } else {
            title = findViewById(R.id.textView_FinanceCatTitle);
            title.setVisibility(View.GONE);
            recyclerView = findViewById(R.id.FinanceProducts);
            recyclerView.setVisibility(View.GONE);
        }
    }*/

    public void ShowDetails(String UID, String name, String image, String price, String description, String game, String rating, CustomerModel customer) {
        Intent intent = new Intent(HomeCustomerActivity.this, ProductCustomerActivity.class);
        intent.putExtra("UID", UID);
        intent.putExtra("name", name);
        intent.putExtra("price", price);
        intent.putExtra("description", description);
        intent.putExtra("image", image);
        intent.putExtra("game", game);
        intent.putExtra("rating", rating);
        intent.putExtra("wishlist", customer.wishlist);
        intent.putExtra("user", customer);
        startActivityForResult(intent, 1);
    }
}