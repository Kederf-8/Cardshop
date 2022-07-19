package com.example.cardshop.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.cardshop.R;
import com.example.cardshop.model.CardModel;
import com.example.cardshop.model.CustomerModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class CustomerCardAdapter extends ArrayAdapter<CardModel> {

    public CustomerCardAdapter(Context context, int textViewResourceId, List<CardModel> objects) {
        super(context, textViewResourceId, objects);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        List<CardModel> wishlist = CustomerModel.getWishlist();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.card_customer, null);
        TextView name = convertView.findViewById(R.id.Nome_item);
        TextView description = convertView.findViewById(R.id.Descrizione_item);
        TextView game = convertView.findViewById(R.id.Game_item);
        TextView price = convertView.findViewById(R.id.Prezzo_item);
        RatingBar rating = convertView.findViewById(R.id.Valutazione_item);
        Switch wished = convertView.findViewById(R.id.Button_wishlist);
        ImageView image = convertView.findViewById(R.id.Image_item);

        // In questo modo si prendono gli attributi specifici dell'elemento contenuto nella lista objects
        // si utilizzano i metodi della classe Items
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

        imageReference.getBytes(MAXBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                image.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        //Si imposta lo switch a true se l'utente nella sua lista desideri salvata nel server ha il prodotto salvato
        /*for (CardModel item : wishlist) {
            if (item.getName().equals(card.getName())) {
                wished.setChecked(true);
            }
        }*/

        //Se lo switch cambia di stato l'item viene aggiunto o rimosso dalla lista preferiti
        wished.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                CustomerModel.addToTheWishlist(card);
            } else {
                CustomerModel.removeFromWishlist(card);
            }
        });
        return convertView;
    }
}
