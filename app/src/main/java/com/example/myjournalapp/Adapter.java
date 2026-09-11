package com.example.myjournalapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myjournalapp.models.Journal;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.JournalViewHolder> {

    List<Journal> journalList;
    FirebaseFirestore db;

    public Adapter(List<Journal> journalList) {
        this.journalList = journalList;
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.journal_item, parent, false);
        return new JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        Journal journal = journalList.get(position);

        holder.title.setText(journal.getTitle());
        holder.date.setText(journal.getDate());
        holder.desc.setText(journal.getDescription());

        // Card click — Detail screen
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), Detail.class);
            intent.putExtra("title", journal.getTitle());
            intent.putExtra("date",  journal.getDate());
            intent.putExtra("desc",  journal.getDescription());
            v.getContext().startActivity(intent);
        });

        // DELETE button
        holder.deleteBtn.setOnClickListener(v -> {
            db.collection("journals")
                    .document(journal.getId())
                    .delete()
                    .addOnSuccessListener(unused -> {
                        journalList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, journalList.size());
                        Toast.makeText(v.getContext(),
                                "Journal Deleted!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(v.getContext(),
                                    "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        });
    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }

    static class JournalViewHolder extends RecyclerView.ViewHolder {

        TextView title, date, desc;
        Button deleteBtn;

        public JournalViewHolder(@NonNull View itemView) {
            super(itemView);
            title     = itemView.findViewById(R.id.title);
            date      = itemView.findViewById(R.id.date);
            desc      = itemView.findViewById(R.id.desc);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}