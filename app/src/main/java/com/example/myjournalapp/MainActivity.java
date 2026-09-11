package com.example.myjournalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myjournalapp.models.Journal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addJournalBtn;
    Button logoutBtn ,profileBtn;;

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser currentUser;

    List<Journal> journalList;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(this, Signin.class));
            finish();
            return;
        }

        recyclerView = findViewById(R.id.recyclerView);
        addJournalBtn = findViewById(R.id.addJournalBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        profileBtn = findViewById(R.id.profileBtn);

        journalList = new ArrayList<>();
        adapter = new Adapter(journalList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadJournals();

        addJournalBtn.setOnClickListener(v ->
                startActivity(new Intent(this, Addjournal.class))
        );

        logoutBtn.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(this, Signin.class));
            finish();
        });

        profileBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, Profile.class));
        });
    }

    private void loadJournals() {

        if (currentUser == null) return;

        String email = currentUser.getEmail();

        db.collection("journals")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(querySnapshots -> {

                    journalList.clear();

                    for (QueryDocumentSnapshot doc : querySnapshots) {
                        Journal j = doc.toObject(Journal.class);
                        journalList.add(j);
                    }

                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show()
                );
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadJournals();
    }
}