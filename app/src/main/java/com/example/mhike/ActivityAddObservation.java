package com.example.mhike;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class ActivityAddObservation extends AppCompatActivity {
    private EditText editTextObservation;
    private EditText editTextComments;
    private Button buttonSubmit;


    DatabaseHelper observationDatabaseHelper;


    String hikeId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observation);

        Spinner spinnerHike = findViewById(R.id.spinnerHike);
        String[] hikeNameLists = {"Easy", "Intermediate", "Difficult"};

        DatabaseHelper hikeDatabaseHelper = new DatabaseHelper(this);

        ArrayList<Hike> hikes = hikeDatabaseHelper.getAllHikes();


        ArrayAdapter<Hike> adapter = new ArrayAdapter<Hike>(this, android.R.layout.simple_spinner_item, hikes) {

            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setText(getItem(position).getHikeName());
                return textView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                textView.setText(getItem(position).getHikeName());
                return textView;
            }

            @Override
            public long getItemId(int position) {
                // Return the hike ID when getItemId is called
                return getItem(position).getId();
            }
        };

        // Set the dropdown layout style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the Spinner
        spinnerHike.setAdapter(adapter);

        // Initialize UI components
        editTextObservation = findViewById(R.id.editTextObservation);
        editTextComments = findViewById(R.id.editTextComments);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        observationDatabaseHelper = new DatabaseHelper(this, "observationdb", 1);

        spinnerHike.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected hike
                Hike selectedHike = (Hike) parentView.getItemAtPosition(position);

                // Get the hikeId
                hikeId = String.valueOf(selectedHike.getId());
                Log.d("spinner", "hikeId"+ hikeId);
                // Now you can use hikeId as needed
                // For example, you can store it in a variable or pass it to a method
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle case when nothing is selected
            }
        });

        // Set click listener for the submit button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("setOnClickListener", "onClick");
                // Get user input
                String observationText = editTextObservation.getText().toString();
                String commentsText = editTextComments.getText().toString();



                // Create an instance of the Observation class
                  Observation observation = new Observation(Long.parseLong(hikeId), observationText, new Date(), commentsText);
                  Log.d("observation", String.valueOf(observation));
               // Save the observation to the database
                long rowId = observationDatabaseHelper.saveObservation(observation);

                Log.d("rowId", String.valueOf(rowId));

                // Handle the result, show a Toast
                if (rowId != -1) {
                    Toast.makeText(ActivityAddObservation.this, "Observation saved to database", Toast.LENGTH_SHORT).show();
                    // Optionally, you can finish the activity or navigate to another screen

                } else {
                    Toast.makeText(ActivityAddObservation.this, "Failed to save observation", Toast.LENGTH_SHORT).show();
                }
                setResult(RESULT_OK);
                finish();
            }
        });


    }


}
