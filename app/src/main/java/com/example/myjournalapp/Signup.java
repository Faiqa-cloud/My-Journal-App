package com.example.myjournalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myjournalapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Signup extends AppCompatActivity {

    EditText nameEt, emailEt, passEt;
    Button signUpBtn;
    TextView goToLogin;
    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Firebase connect
        auth = FirebaseAuth.getInstance();
        db   = FirebaseFirestore.getInstance();

        // Views
        nameEt       = findViewById(R.id.name);
        emailEt      = findViewById(R.id.email);
        passEt       = findViewById(R.id.password);
        signUpBtn    = findViewById(R.id.signup);
        goToLogin = findViewById(R.id.signin);

        // Buttons
        signUpBtn.setOnClickListener(v -> signupUser());
        goToLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, Signin.class));
        });
    }

    private void signupUser() {
        String name     = nameEt.getText().toString().trim();
        String email    = emailEt.getText().toString().trim();
        String password = passEt.getText().toString().trim();

        // Validation
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase Auth — account banao
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    String uid = auth.getCurrentUser().getUid();
                    User user  = new User(name, email, "");

                    // Firestore mein save karo
                    db.collection("users")
                            .document(uid)
                            .set(user)
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(this, "Signup Success!",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(this, MainActivity.class));
                                finish();
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(this, "DB Error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show()
                            );
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Signup Failed: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show()
                );
    }
}