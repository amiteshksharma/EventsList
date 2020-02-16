package com.example.eventslist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class EventLayout extends AppCompatActivity {

    public static final String NOTE_TITLE = "note_title";
    public static final String NOTE_DESCRIPTION = "note_description";
    public static final String NOTE_DATE = "note_date";
    public static final String NOTE_LOCATION = "note_location";

    private String titleS, locationS, dateS, descriptionS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_layout);

        TextView title = findViewById(R.id.titleOfEvents);
        TextView location = findViewById(R.id.locationLayout);
        TextView date = findViewById(R.id.dateLayout);
        TextView description = findViewById(R.id.descriptionLayout);

        if(getIntent().getExtras() != null){
            titleS = getIntent().getStringExtra(NOTE_TITLE);
            descriptionS = getIntent().getStringExtra(NOTE_DESCRIPTION);
            dateS = getIntent().getStringExtra(NOTE_DATE);
            locationS = getIntent().getStringExtra(NOTE_LOCATION);

        }

        title.setText(titleS);
        description.setText(descriptionS);
        date.setText(dateS);
        location.setText(locationS);
    }
}
