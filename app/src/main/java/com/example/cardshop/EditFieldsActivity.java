package com.example.cardshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cardshop.model.CardModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class EditFieldsActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private Uri filePath;
    private CardModel card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fields);
        Objects.requireNonNull(getSupportActionBar()).hide(); //nasconde la barra superiore
        card = (CardModel) getIntent().getSerializableExtra("Card");
        setCardFields();
        Button modify = findViewById(R.id.editImageButton);
        modify.setOnClickListener(v -> SelectImage());
        Button applyChanges = findViewById(R.id.editModifyButton);
        applyChanges.setOnClickListener(view -> {
            EditText nameText = findViewById(R.id.editTextName);
            String name = "";
            if (nameText != null) name = nameText.toString();

            EditText descText = findViewById(R.id.editTextDescription);
            String description = "";
            if (descText != null) description = descText.toString();

            EditText priceText = findViewById(R.id.editTextPrice);
            String price = "";
            if (priceText != null) price = priceText.toString();

            EditText gameText = findViewById(R.id.editTextGame);
            String game = "";
            if (gameText != null) game = gameText.toString();

            EditText rateText = findViewById(R.id.editTextRating);
            Double rating = 0.0d;
            if (rateText != null) rating = Double.valueOf(rateText.toString());

            String image = addImage();

            if (!name.equals(card.getName())) updateField("name", name);
            if (!description.equals(card.getDescription())) updateField("description", description);
            if (!price.equals(card.getPrice())) updateField("price", price);
            if (!game.equals(card.getGame())) updateField("game", game);
            if (!rating.equals(card.getRating())) updateField("rating", rating);
            if (!image.equals(card.getImage())) updateField("image", image);
        });
    }

    public void goBack(View view){
        startActivity(new Intent(this, ModifyProductActivity.class));
        finish();
    }

    public void setCardFields() {
        EditText editName = findViewById(R.id.editTextName_edit);
        editName.setText(card.getName());
        EditText editDesc = findViewById(R.id.editTextDescription_edit);
        editDesc.setText(card.getDescription());
        EditText editPrice = findViewById(R.id.editTextPrice_edit);
        editPrice.setText(card.getPrice());
        EditText editGame = findViewById(R.id.editTextGame_edit);
        editGame.setText(card.getGame());
        EditText editRating = findViewById(R.id.editTextRating_edit);
        editRating.setText(String.valueOf(card.getRating()));
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        int PICK_IMAGE_REQUEST = 22;
        startActivityForResult(Intent.createChooser(intent, "Select image"), PICK_IMAGE_REQUEST);
        //uploadImage();
    }

    public void updateField(String field, String value) {
        firestore.collection("cards").document()
                .update(field, value);
    }

    public void updateField(String field, Double value) {
        firestore.collection("cards").document()
                .update(field, value);
    }

    public String addImage() {
        //utilizzato per selezionare il nome dell'immagine correttamente
        if (filePath != null) {
            String path = filePath.getPath();
            int index = path.lastIndexOf('/');
            String name = path.substring(index);
            path = "/immaginiCarte" + name;
            return path;
        } else return null;
    }
}