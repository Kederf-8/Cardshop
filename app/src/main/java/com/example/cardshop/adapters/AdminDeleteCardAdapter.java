package com.example.cardshop.adapters;

import android.content.Context;
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

import com.example.cardshop.R;
import com.example.cardshop.model.AdminModel;
import com.example.cardshop.model.CardModel;
import com.example.cardshop.model.CustomerModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdminDeleteCardAdapter extends ArrayAdapter<CardModel> {
    public AdminDeleteCardAdapter(Context context, int textViewResourceId, List<CardModel> objects) {
        super(context, textViewResourceId, objects);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.card_admin_delete, null);
        TextView name = convertView.findViewById(R.id.nameCardDelete);
        TextView description = convertView.findViewById(R.id.descriptionCardDelete);
        TextView game = convertView.findViewById(R.id.gameCardDelete);
        TextView price = convertView.findViewById(R.id.priceCardDelete);
        RatingBar rating = convertView.findViewById(R.id.ratingCardDelete);
        ImageButton delete = convertView.findViewById(R.id.deleteButton);
        ImageView image = convertView.findViewById(R.id.imageCardDelete);

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

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        delete.setOnClickListener(view -> {
            System.out.println("Cancellazione prodotto: " + card.getName());
            firestore.collection("cards").document(card.getName())
                    .delete()
                    .addOnSuccessListener(e -> System.out.println("Cancellazione effettuata"))
                    .addOnFailureListener(e -> System.out.println("Cancellazione fallita"));
        });

        return convertView;
    }
}
