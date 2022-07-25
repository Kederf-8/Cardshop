package com.example.cardshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        Button modifyImage = findViewById(R.id.editImageButton);
        modifyImage.setOnClickListener(v -> SelectImage());
        Button applyChanges = findViewById(R.id.editModifyButton);
        applyChanges.setOnClickListener(view -> {
            String nameText = ((EditText) findViewById(R.id.editTextName_edit)).getText().toString();
            String descText = ((EditText) findViewById(R.id.editTextDescription_edit)).getText().toString();
            String priceText = ((EditText) findViewById(R.id.editTextPrice_edit)).getText().toString();
            String gameText = ((EditText) findViewById(R.id.editTextGame_edit)).getText().toString();
            Double rateText = Double.parseDouble(((EditText) findViewById(R.id.editTextRating_edit)).getText().toString());

            String image = addImage();

            if (!nameText.equals(card.getName())) updateField("name", nameText);
            if (!descText.equals(card.getDescription())) updateField("description", descText);
            if (!priceText.equals(card.getPrice())) updateField("price", priceText);
            if (!gameText.equals(card.getGame())) updateField("game", gameText);
            if (!rateText.equals(card.getRating())) updateField("rating", rateText);
            if (image != null && !image.equals(card.getImage())) updateField("image", image);
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
                .update(field, value)
                .addOnSuccessListener(e -> Toast.makeText(this,"Updated successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show());
    }

    public void updateField(String field, Double value) {
        firestore.collection("cards").document()
                .update(field, value)
                .addOnSuccessListener(e -> Toast.makeText(this,"Updated successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show());
    }

    public String addImage() {
        if (filePath != null) {
            String path = filePath.getPath();
            int index = path.lastIndexOf('/');
            String name = path.substring(index);
            path = "immaginiCarte" + name;
            return path;
        } else return null;
    }
}