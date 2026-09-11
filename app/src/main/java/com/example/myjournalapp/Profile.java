package com.example.myjournalapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {

    TextView profileName, profileEmail;
    Button backbtn;
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth        = FirebaseAuth.getInstance();
        db          = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser();

        profileName  = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        backbtn      = findViewById(R.id.backbtn);

        // Email seedha Auth se
        profileEmail.setText(currentUser.getEmail());

        // Back button
        backbtn.setOnClickListener(v -> finish());

        // Firestore se name lo — "name" field use karo
        db.collection("users")
                .document(currentUser.getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    // ✅ "name" — User.java se match karta hai
                    String name = documentSnapshot.getString("name");
                    if (name != null) {
                        profileName.setText(name);
                    } else {
                        profileName.setText("Name not found");
                    }
                })
                .addOnFailureListener(e -> {
                    profileName.setText("Error loading name");
                });
    }
}