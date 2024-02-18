package com.example.mhike;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static com.example.mhike.DatabaseHelper.COLUMN_ADDITIONAL_COMMENTS;
import static com.example.mhike.DatabaseHelper.COLUMN_OBSERVATION;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.File;

public class EditObservationActivity extends AppCompatActivity {
    DatabaseHelper observationDatabaseHelper;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123; // Use any unique integer

    Button editButtonSubmit;
    private long observationId;
    private String observationData,additionalCmt;

    TextView editObservation,editComments;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_observation);

        editObservation = findViewById(R.id.editObservation);
        editComments = findViewById(R.id.editComments);
        editButtonSubmit = findViewById(R.id.editButtonSubmit);

        Log.d("in edit", "in edit observation");

        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            observationId = receivedIntent.getLongExtra("observationId", -1);;
           Log.d("observationId", "in edit observation"+observationId);
            observationDatabaseHelper = new DatabaseHelper(this, "observationdb", 1);
            Cursor cursor =observationDatabaseHelper.getObservationById(observationId);
            if (cursor != null && cursor.moveToFirst()) {
                Log.d("cursor","not null");
                observationData = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OBSERVATION));
                additionalCmt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDITIONAL_COMMENTS));
                //String imgPath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH));
                Log.d("cursor", "observationData..."+observationData);
                Log.d("cursor", "additionalCmt..."+additionalCmt);
                editObservation.setText(observationData);
                editComments.setText(additionalCmt);

            } else {

            }


                cursor.close();
           // }

        }

        editButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observation observation = new Observation(Integer.parseInt(String.valueOf(observationId)),observationData,additionalCmt);
                observation.setObservation(editObservation.getText().toString());
                observation.setAdditionalComments(editComments.getText().toString());
                observationDatabaseHelper.updateObservation(observation);
                Log.d("cursor", "edit success...");
            }
        });

    }


//

}
