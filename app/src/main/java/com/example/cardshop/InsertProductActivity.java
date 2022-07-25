package com.example.cardshop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InsertProductActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static FirebaseFirestore firestore;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_product);
        Objects.requireNonNull(getSupportActionBar()).hide(); //nasconde la barra superiore
        firestore = FirebaseFirestore.getInstance();
        Button select = findViewById(R.id.buttonImage);
        select.setOnClickListener(v -> SelectImage());
    }

    public void goBack(View view) {
        startActivity(new Intent(this, HomeAdminActivity.class));
        finish();
    }

    public String addImage() {
        String path = filePath.getPath();
        int index = path.lastIndexOf('/');
        String name = path.substring(index);
        path = "immaginiCarte" + name + ".jpg";
        return path;
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), PICK_IMAGE_REQUEST);
        //uploadImage();
    }

    public void insertCard(View view) {
        Map<String, Object> docData = new HashMap<>();
        String name = ((EditText) findViewById(R.id.editTextName)).getText().toString();
        docData.put("name", name);
        docData.put("description", ((EditText) findViewById(R.id.editTextDescription)).getText().toString());
        docData.put("price", ((EditText) findViewById(R.id.editTextPrice)).getText().toString());
        docData.put("game", ((EditText) findViewById(R.id.editTextGame)).getText().toString());
        docData.put("rating", Double.parseDouble(((EditText) findViewById(R.id.editTextRating)).getText().toString()));
        docData.put("image", addImage());

        uploadImage(name);

        firestore.collection("cards")
                .add(docData)
                .addOnSuccessListener(e -> Toast.makeText(InsertProductActivity.this, "New card added successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(InsertProductActivity.this, "Insertion failed", Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadImage(String name) {
        FirebaseStorage.getInstance().getReference()
                .child("immaginiCarte/" + name + ".jpg");
    }
}