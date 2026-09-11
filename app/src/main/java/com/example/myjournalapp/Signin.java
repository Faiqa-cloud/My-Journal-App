package com.example.myjournalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class Signin extends AppCompatActivity {

    EditText emailEt, passEt;
    Button loginBtn;
    FirebaseAuth auth;
    TextView goToSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        auth = FirebaseAuth.getInstance();

        emailEt      = findViewById(R.id.email);
        passEt       = findViewById(R.id.password);
        loginBtn     = findViewById(R.id.signinbtn);
        goToSignUp = findViewById(R.id.signup);

        loginBtn.setOnClickListener(v -> loginUser());
        goToSignUp.setOnClickListener(v -> {
            startActivity(new Intent(this, Signup.class));
        });
    }

    private void loginUser() {
        String email    = emailEt.getText().toString().trim();
        String password = passEt.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fill all fields",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(this, "Login Success!",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Wrong email or password!",
                                Toast.LENGTH_SHORT).show()
                );
    }
}