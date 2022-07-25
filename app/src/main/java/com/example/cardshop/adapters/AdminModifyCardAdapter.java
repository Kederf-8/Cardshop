package com.example.cardshop.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.cardshop.EditFieldsActivity;
import com.example.cardshop.ModifyProductActivity;
import com.example.cardshop.R;
import com.example.cardshop.model.CardModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdminModifyCardAdapter extends ArrayAdapter<CardModel> {

    Context context;

    public AdminModifyCardAdapter(Context context, int textViewResourceId, List<CardModel> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.card_admin_modify, null);
        TextView name = convertView.findViewById(R.id.nameCardModify);
        TextView description = convertView.findViewById(R.id.descriptionCardModify);
        TextView game = convertView.findViewById(R.id.gameCardModify);
        TextView price = convertView.findViewById(R.id.priceCardModify);
        RatingBar rating = convertView.findViewById(R.id.ratingCardModify);
        ImageButton modify = convertView.findViewById(R.id.modifyButton);
        ImageView image = convertView.findViewById(R.id.imageCardModify);

        CardModel card = getItem(position);
        name.setText(card.getName());
        description.setText(card.getDescription());
        game.setText(card.getGame());
        price.setText(card.getPrice());
        rating.setRating((float) Double.parseDouble(String.valueOf(card.getRating())));

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        StorageReference imageReference = storageReference.child(card.getImage());

        long MAXBYTES = 1024 * 1024;

        imageReference.getBytes(MAXBYTES).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            image.setImageBitmap(bitmap);
        }).addOnFailureListener(e -> {});

        modify.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), EditFieldsActivity.class);
            intent.putExtra("Card", card);
            context.startActivity(intent);
        });

        return convertView;
    }
}
