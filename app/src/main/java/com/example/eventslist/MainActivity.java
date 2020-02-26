package com.example.eventslist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.eventslist.EventLayout.NOTE_DATE;
import static com.example.eventslist.EventLayout.NOTE_DESCRIPTION;
import static com.example.eventslist.EventLayout.NOTE_LOCATION;
import static com.example.eventslist.EventLayout.NOTE_TITLE;

public class MainActivity extends AppCompatActivity implements NotesAdapter.OnNoteListener {

    private FloatingActionButton button;
    private RecyclerView recyclerView;
    private NotesAdapter notesAdapter;
    private List<NoteValues> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        button = findViewById(R.id.createTile);

        list = new ArrayList<>();

        recyclerView = findViewById(R.id.eventList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getTiles();

        openCreateEvent();

        setSupportActionBar(toolbar);
    }

    private void getTiles(){
        FirebaseDatabase.getInstance().getReference().child("Tiles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<NoteValues> temp = new ArrayList<>();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NoteValues noteValues = new NoteValues();

                    noteValues.setTitle(snapshot.child("title").getValue().toString());
                    noteValues.setLocation(snapshot.child("location").getValue().toString());
                    noteValues.setDescription(snapshot.child("description").getValue().toString());
                    noteValues.setDate(snapshot.child("date").getValue().toString());

                    temp.add(noteValues);
                }
                list.addAll(temp);
                notesAdapter = new NotesAdapter(getApplicationContext(), temp, MainActivity.this);
                recyclerView.setAdapter(notesAdapter);
                notesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void openCreateEvent() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.logoutButton){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNoteClick(int position) {
        NoteValues noteValues = list.get(position);
        Intent intent = new Intent(this, EventLayout.class);
        intent.putExtra(NOTE_TITLE, noteValues.getTitle());
        intent.putExtra(NOTE_DATE, noteValues.getDate());
        intent.putExtra(NOTE_DESCRIPTION, noteValues.getDescription());
        intent.putExtra(NOTE_LOCATION, noteValues.getLocation());
        startActivity(intent);
    }

    @Override
    public void onNoteLongClick(final int position) {
        new AlertDialog.Builder(this).setTitle(R.string.app_name).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NoteValues note= list.get(position);
                String title = note.getTitle();
                FirebaseDatabase.getInstance().getReference().child("Tiles").child(title).removeValue();
                getTiles();
            }
        }).create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTiles();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
