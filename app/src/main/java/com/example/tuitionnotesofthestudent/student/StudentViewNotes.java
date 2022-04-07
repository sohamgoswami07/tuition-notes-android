package com.example.tuitionnotesofthestudent.student;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuitionnotesofthestudent.Model.NotesModel;
import com.example.tuitionnotesofthestudent.R;
import com.example.tuitionnotesofthestudent.adapter.studentViewAssignmentAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentViewNotes extends AppCompatActivity {

    ArrayList<NotesModel> arrayList = new ArrayList<>();
    studentViewAssignmentAdapter adapter;
    RecyclerView rv_viewnotes;

    Intent intent;
    String childName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_notes);

        rv_viewnotes = findViewById(R.id.rv_viewNotes);
        rv_viewnotes.setHasFixedSize(true);
        rv_viewnotes.setLayoutManager(new LinearLayoutManager(StudentViewNotes.this));

        intent = getIntent();
        childName = intent.getStringExtra("childName");
        Toast.makeText(StudentViewNotes.this, "child name : " + childName, Toast.LENGTH_SHORT).show();

        loadNotes();
    }

    private void loadNotes() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("TAG1", "inside load notes");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("notes");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("TAG1", "loading notes");
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        NotesModel model = dataSnapshot1.getValue(NotesModel.class);
                        Log.d("TAG1", "onDataChange: " + dataSnapshot1);
                        arrayList.add(model);
                    }
                }
                Log.d("TAG1", "arraylist size : " + arrayList.size());
                adapter = new studentViewAssignmentAdapter(StudentViewNotes.this, arrayList, childName);
                rv_viewnotes.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}