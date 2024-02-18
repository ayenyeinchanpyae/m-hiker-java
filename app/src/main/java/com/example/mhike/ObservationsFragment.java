package com.example.mhike;

// ObservationsFragment.java
import static com.example.mhike.DatabaseHelper.COLUMN_ADDITIONAL_COMMENTS;
import static com.example.mhike.DatabaseHelper.COLUMN_OBSERVATION;
import static com.example.mhike.DatabaseHelper.HIKE_DESCRIPTION;
import static com.example.mhike.DatabaseHelper.HIKE_DIFFICULTY_LEVEL;
import static com.example.mhike.DatabaseHelper.HIKE_LENGTH;
import static com.example.mhike.DatabaseHelper.HIKE_PARKING;
import static com.example.mhike.DatabaseHelper.HIKE_RECOMMENDED_GEAR;
import static com.example.mhike.DatabaseHelper.HIKE_TRAIL_CONDITION;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class ObservationsFragment extends Fragment {

    private TextView textViewObservation, textViewComment, textViewTimeOfObservation;
    private String hikeId;

    DatabaseHelper observationDatabaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("ObservationsFragment", "onCreateView");
        View view = inflater.inflate(R.layout.activity_observations_fragment, container, false);

        if (getArguments() != null) {
            hikeId = getArguments().getString("hikeId");
            Log.d("ObservationsFragment", "hikeId: " + hikeId);
        }
//        textViewObservation = view.findViewById(R.id.textViewObservation);
//        textViewComment = view.findViewById(R.id.textViewComment);
        // textViewTimeOfObservation = view.findViewById(R.id.textViewComment);

        //Bundle bundle = getArguments();
//        if (bundle != null) {
//            hikeId = bundle.getString("hikeId");
//            Log.d("hikeId", " bundle: " + hikeId);
//
//            observationDatabaseHelper = new DatabaseHelper(requireContext(), "observationdb", 1);
//
//            Cursor cursor = observationDatabaseHelper.getObservationsByHikeId(Long.parseLong(hikeId));
//            if (cursor != null && cursor.moveToFirst()) {
//                // Retrieve data from the cursor and populate your UI elements
//                // String observation, comment , timeOfObservation ;
//                String observation = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OBSERVATION));
//                String comment = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDITIONAL_COMMENTS));
//                cursor.close();
//                Log.d("observation", " observation: " + observation);
//                Log.d("comment", " comment: " + comment);
//                if (textViewObservation != null) {
//                    Log.d("textViewObservation", " observation: " + observation);
//                    textViewObservation.setText("abc");
//                } else {
//                    Log.e("TextViewError", "textViewObservation is null");
//                }
//            }
//        }
//        Button buttonAddObservation = view.findViewById(R.id.addObservation);
//        buttonAddObservation.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (getArguments() != null) {
//                    hikeId = getArguments().getString("hikeId");
//                    Intent intent = new Intent(getActivity(), ActivityAddObservation.class);
//                    intent.putExtra("hikeId", "add to intent" + hikeId);
//                    Log.d("hikeId", " pass to add: " + hikeId);
//                    startActivity(intent);
//                }
//
//
//            }
//        });

        return view;
    }
}


