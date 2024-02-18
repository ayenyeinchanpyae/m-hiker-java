package com.example.mhike;

// HikeInfoFragment.java
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import static com.example.mhike.DatabaseHelper.HIKE_DESCRIPTION;
import static com.example.mhike.DatabaseHelper.HIKE_DIFFICULTY_LEVEL;
import static com.example.mhike.DatabaseHelper.HIKE_LENGTH;
import static com.example.mhike.DatabaseHelper.HIKE_PARKING;
import static com.example.mhike.DatabaseHelper.HIKE_RECOMMENDED_GEAR;
import static com.example.mhike.DatabaseHelper.HIKE_TRAIL_CONDITION;
import com.example.mhike.ObservationsFragment;
public class HikeInfoFragment extends Fragment {
    private TextView hikeName;
    private TextView hikeLocation;
    private TextView hikeDate;
    private TextView hikeLength;
    private TextView hikeParking;
    private TextView hikeDifficulity;
    private TextView hikeDescription;
    private TextView hikeTrailCondition;
    private TextView hikeRecommendedGear;
    private ImageButton hikeEditBtn;
    private ImageButton hikeDeleteBtn;

    private String hikeId;
    private String name;
    private String location;
    private String date;
    public HikeInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_hike_info_fragment, container, false);
        String hikeId, name, date, location, length, description, trailConditions, recommendedGears, difficulityLvl, parking;
        hikeName = view.findViewById(R.id.hikeName);
        hikeLocation = view.findViewById(R.id.hikeLocation);
        hikeDate = view.findViewById(R.id.hikeDate);
        hikeLength = view.findViewById(R.id.hikeLength);
        hikeParking = view.findViewById(R.id.hikeParking);
        hikeDifficulity = view.findViewById(R.id.hikeDifficulity);
        hikeDescription = view.findViewById(R.id.hikeDescription);
        hikeTrailCondition = view.findViewById(R.id.hikeTrailCondition);
        hikeRecommendedGear = view.findViewById(R.id.hikeRecommendedGear);
        hikeEditBtn = view.findViewById(R.id.hikeEditBtn);
        hikeDeleteBtn = view.findViewById(R.id.hikeDeleteBtn);

        // Get data from the Intent
        Intent intent = requireActivity().getIntent();
        hikeId = String.valueOf(intent.getLongExtra("id", -1));
        name = intent.getExtras().getString("name");
        location = intent.getExtras().getString("location");
        date = intent.getExtras().getString("date");

        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());

        Cursor cursor = databaseHelper.getRowById(Long.parseLong(hikeId));

        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve data from the cursor and populate your UI elements

            length = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_LENGTH));
            description = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_DESCRIPTION));
            trailConditions = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_TRAIL_CONDITION));
            recommendedGears = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_RECOMMENDED_GEAR));
            difficulityLvl = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_DIFFICULTY_LEVEL));
            parking = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_PARKING));

            cursor.close();
        }

        hikeName.setText(name);
        hikeLocation.setText(location);
        hikeDate.setText(date);
        hikeParking.setText(date);
        hikeLength.setText(name);
        hikeDifficulity.setText(name);
        hikeDescription.setText(name);
        hikeTrailCondition.setText(name);
        hikeRecommendedGear.setText(name);

        hikeEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), ModifyHikeActivity.class);

                intent.putExtra("id",hikeId);
                intent.putExtra("name",name);
                intent.putExtra("location",location);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });

        hikeDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

                builder.setMessage("Are you sure you want to delete?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.d("MyTag",hikeId);
                                databaseHelper.deleteHike(Integer.parseInt(hikeId));
                                Intent goToCertainPage = new Intent(requireContext(), MainActivity.class);
                                startActivity(goToCertainPage);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User canceled the operation, do nothing or dismiss the dialog
                                dialog.dismiss();
                            }
                        });
                // Create and show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        Log.d("hikeId", " hikeInfoFragment: " + hikeId);

        ObservationsFragment observationFragment = new ObservationsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("hikeId", hikeId);
        observationFragment.setArguments(bundle);

        // Begin a Fragment transaction.
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

        // Replace the contents of the container with the new fragment.
        ft.replace(R.id.fragment_container, observationFragment);

        // Complete the changes added above.
        ft.commit();

        return view;

    }


}

