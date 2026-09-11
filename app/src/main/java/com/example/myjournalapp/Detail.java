package com.example.myjournalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Detail extends AppCompatActivity {

    TextView detailTitle, detailDate, detailDesc;
    Button backBtn;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailTitle = findViewById(R.id.detailTitle);
        detailDate  = findViewById(R.id.detailDate);
        detailDesc  = findViewById(R.id.detailDesc);
        backBtn = findViewById(R.id.detailBackBtn);

        backBtn.setOnClickListener(v->{
            startActivity(new Intent(this, MainActivity.class));
        });



        String title = getIntent().getStringExtra("title");
        String date  = getIntent().getStringExtra("date");
        String desc  = getIntent().getStringExtra("desc");

        detailTitle.setText(title);
        detailDate.setText(date);
        detailDesc.setText(desc);
    }


}