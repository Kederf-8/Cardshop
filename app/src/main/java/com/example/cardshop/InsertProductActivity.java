package com.example.cardshop;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InsertProductActivity extends AppCompatActivity {

    private static FirebaseFirestore firestore;
    Uri filePath;
    Button select;
    final int PICK_IMAGE_REQUEST = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_product);
        Objects.requireNonNull(getSupportActionBar()).hide(); //nasconde la barra superiore
        firestore = FirebaseFirestore.getInstance();
        select = findViewById(R.id.buttonImage);
        select.setOnClickListener(v -> SelectImage());
    }

    public void goBack(View view) {
        startActivity(new Intent(this, HomeAdminActivity.class));
        finish();
    }

    public String addImage() {
        //utilizzato per selezionare il nome dell'immagine correttamente
        String path = filePath.getPath();
        int index = path.lastIndexOf('/');
        String name = path.substring(index);
        path = "/immaginiCarte" + name;
        return path;
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleziona l'immagine"), PICK_IMAGE_REQUEST);
        //uploadImage();
    }

    public void insertCard(View view) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("name", ((EditText) findViewById(R.id.editTextName)).getText().toString());
        docData.put("description", ((EditText) findViewById(R.id.editTextDescription)).getText().toString());
        docData.put("price", ((EditText) findViewById(R.id.editTextPrice)).getText().toString());
        docData.put("game", ((EditText) findViewById(R.id.editTextGame)).getText().toString());
        docData.put("rating", Double.parseDouble(((EditText) findViewById(R.id.editTextRating)).getText().toString()));
        docData.put("image", addImage());

        firestore.collection("cards")
                .add(docData)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));
    }

    // Override metodo onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view

        // si controlla il requestiCode e il resulCode
        // se requestCode è uguale a PICK_IMAGE_REQUEST e resultCode è uguale a RESULT_OK
        // si può settare l'immagine
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // si ottiene il path
            filePath = data.getData();
            try {
                // si setta l'immagine con il metodo della bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                //in caso di eccezioni si stampano
                e.printStackTrace();
            }
        }
    }

    public void uploadImage(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(InsertProductActivity.this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InsertProductActivity.this, "Failure to upload", Toast.LENGTH_SHORT).show();
            }
        });
    }
}