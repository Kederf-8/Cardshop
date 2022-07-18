package com.example.cardshop.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cardshop.HomeCustomerActivity;
import com.example.cardshop.R;
import com.example.cardshop.model.CardModel;
import com.example.cardshop.model.CustomerModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;

public class CustomerCardAdapter extends RecyclerView.Adapter<CustomerCardAdapter.ViewHolder> {
    private static final String listID = "dynamicProductListAdmin";
    private ArrayList<CardModel> products;
    private final HomeCustomerActivity homeCustomerActivity;
    private final CustomerModel customer;

    public CustomerCardAdapter(Context context, ArrayList<CardModel> products, HomeCustomerActivity homeCustomerActivity, CustomerModel customer) {
        this.products = products;
        this.homeCustomerActivity = homeCustomerActivity;
        this.customer = customer;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_customer_product, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(products.get(position).image);

        holder.UID.setText(products.get(position).UID);
        holder.name.setText(products.get(position).name);
        holder.price.setText(products.get(position).price);
        holder.description.setText(products.get(position).description);
        holder.game.setText(products.get(position).game);
        //GlideApp.with(context).asBitmap().load(storageReference).into(holder.image);
        holder.ratingBar.setRating(products.get(position).rating);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UID = products.get(position).UID;
                String name = products.get(position).name;
                String image = products.get(position).image;
                String price = products.get(position).price;
                String description = products.get(position).description;
                String game = products.get(position).game;
                String rating = products.get(position).rating.toString();
                homeCustomerActivity.ShowDetails(UID, name, price, description, game, image, rating, customer);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView UID;
        TextView name;
        TextView price;
        TextView description;
        TextView game;
        ImageView image;
        RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            /*image = itemView.findViewById(R.id.cardCustomerProduct_Photo);
            name = itemView.findViewById(R.id.cardCustomerProduct_Title);
            price = itemView.findViewById(R.id.cardCustomerProduct_Price);
            ratingBar = itemView.findViewById(R.id.cardCustomerProduct_RatingBar);
            game = itemView.findViewById(R.id.cardCustomerProduct_Category);
            UID = itemView.findViewById(R.id.cardCustomerProduct_UID);
            description = itemView.findViewById(R.id.cardCustomerProduct_Description);*/
        }
    }
}