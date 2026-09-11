package com.example.myjournalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myjournalapp.models.Journal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Addjournal extends AppCompatActivity {

    EditText titleEt;
    TextView descEt;
    Button saveBtn,bcktohomebtn;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addjournal);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser();

        titleEt = findViewById(R.id.title);
        descEt  = findViewById(R.id.desc);
        saveBtn = findViewById(R.id.savebtn);
        bcktohomebtn = findViewById(R.id.bcktohomebtn);

        saveBtn.setOnClickListener(v -> saveJournal());
        bcktohomebtn.setOnClickListener(v->{
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    private void saveJournal() {

        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String title = titleEt.getText().toString().trim();
        String desc  = descEt.getText().toString().trim();

        if (title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = new SimpleDateFormat("dd MMM yyyy",
                Locale.getDefault()).format(new Date());

        String id = db.collection("journals").document().getId();

        Journal journal = new Journal(
                currentUser.getDisplayName() != null ?
                        currentUser.getDisplayName() : "User",
                id,
                date,
                desc,
                title,
                currentUser.getEmail()
        );

        db.collection("journals")
                .document(id)
                .set(journal)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Journal Saved!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show()
                );
    }
}