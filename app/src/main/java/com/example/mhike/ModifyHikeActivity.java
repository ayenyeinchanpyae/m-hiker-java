package com.example.mhike;


import static com.example.mhike.DatabaseHelper.HIKE_DATE;
import static com.example.mhike.DatabaseHelper.HIKE_DESCRIPTION;
import static com.example.mhike.DatabaseHelper.HIKE_DIFFICULTY_LEVEL;
import static com.example.mhike.DatabaseHelper.HIKE_LENGTH;
import static com.example.mhike.DatabaseHelper.HIKE_LOCATION;
import static com.example.mhike.DatabaseHelper.HIKE_NAME;
import static com.example.mhike.DatabaseHelper.HIKE_PARKING;
import static com.example.mhike.DatabaseHelper.HIKE_RECOMMENDED_GEAR;
import static com.example.mhike.DatabaseHelper.HIKE_TRAIL_CONDITION;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;

public class ModifyHikeActivity extends AppCompatActivity {

    EditText modifyHikeName;
    EditText editHikeLocation;
    EditText modifyHikeDate;
    String modifyRadioParkingAvailable;
    EditText modifyHikeLength;
    Spinner modifyDifficultySpinner;
    EditText modifyDescription;
    EditText hikeId;
    EditText modifyTrailConditions;
    EditText modifyRecommendedGears;
    RadioButton radioButton1;
    RadioButton radioButton2;
    Button btnUpdate, btnDelete;

    ImageButton btnSaveQualification, btnQList;

    String id, name, date, location, length, description, trailConditions, recommendedGears, difficulityLvl, parking;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_hike);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
//        name = intent.getExtras().getString("name");
//        location = intent.getExtras().getString("location");
//        date = intent.getExtras().getString("date");

        modifyHikeName = findViewById(R.id.hikeName);
        editHikeLocation = findViewById(R.id.modifyHikeLocation);
        modifyHikeDate = findViewById(R.id.modifyHikeDate);
        modifyHikeLength = findViewById(R.id.modifyHikeLength);
        modifyDescription = findViewById(R.id.modifyDescription);
        modifyTrailConditions = findViewById(R.id.modifyTrailConditions);
        modifyRecommendedGears = findViewById(R.id.modifyRecommendedGears);
        modifyDifficultySpinner = findViewById(R.id.modifyDifficultySpinner);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);

        btnUpdate = findViewById(R.id.submitButton);
        // btnDelete = findViewById(R.id.btnDelete);
//        btnSaveQualification = findViewById(R.id.btn_save_qualification);
//        btnQList =findViewById(R.id.btn_q_list);



        databaseHelper = new DatabaseHelper(this);

        Cursor cursor = databaseHelper.getRowById(Long.parseLong(id));

        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve data from the cursor and populate your UI elements
            name = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_NAME));
            location = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_LOCATION));
            date = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_DATE));
            length = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_LENGTH));
            description = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_DESCRIPTION));
            trailConditions = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_TRAIL_CONDITION));
            recommendedGears = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_RECOMMENDED_GEAR));
            difficulityLvl = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_DIFFICULTY_LEVEL));
            modifyRadioParkingAvailable = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_PARKING));

            cursor.close();
        }
        String[] difficultyLevels = new String[]{"Easy", "Medium", "Hard", "Expert"};

        Spinner modifyDifficultySpinner = findViewById(R.id.modifyDifficultySpinner);

        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, difficultyLevels);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        modifyDifficultySpinner.setAdapter(difficultyAdapter);

        int index = Arrays.asList(difficultyLevels).indexOf(difficulityLvl);

        modifyDifficultySpinner.setSelection(index);
        modifyHikeName.setText(name);
        editHikeLocation.setText(location);
        modifyHikeDate.setText(date);
        modifyHikeLength.setText(length);
        modifyDescription.setText(description);
        modifyTrailConditions.setText(trailConditions);
        modifyRecommendedGears.setText(recommendedGears);

        if (modifyRadioParkingAvailable.equals("Yes")) {
            radioButton1.setChecked(true);
        } else if (modifyRadioParkingAvailable.equals("No")) {
            radioButton2.setChecked(true);
        }

//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                databaseHelper.deleteHike(Integer.parseInt(id));//type casting
//
//                Intent listIntent = new Intent(ModifyUserActivity.this, ListUserActivity.class);
//                startActivity(listIntent);
//            }
//        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hike hike = new Hike(Integer.parseInt(id),name,
                        location,
                        date,
                        difficulityLvl,
                        Integer.parseInt(length),
                        parking,
                        description,
                        trailConditions,
                        recommendedGears);
                // Assuming you have a Hike object named 'hike' that you want to update
                hike.setHikeName(modifyHikeName.getText().toString());
                hike.setLocation(editHikeLocation.getText().toString());
                hike.setDate(modifyHikeDate.getText().toString());
                hike.setHikeLength(Integer.valueOf(modifyHikeLength.getText().toString()));
                hike.setDifficultyLevel(modifyDifficultySpinner.getSelectedItem().toString());
                hike.setRecommendedGear(modifyRecommendedGears.getText().toString());
                hike.setTrailConditions(modifyTrailConditions.getText().toString());
                hike.setDescription(modifyDescription.getText().toString());
                RadioGroup radioGroup = findViewById(R.id.modifyRadioParkingAvailable);
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                    String selectedValue = selectedRadioButton.getText().toString();

                    // Assuming "Yes" or "No" as radio button text
                    if ("Yes".equals(selectedValue)) {
                        modifyRadioParkingAvailable = "Yes";
                    } else if ("No".equals(selectedValue)) {
                        modifyRadioParkingAvailable = "No";
                    }
                }
                hike.setParkingAvailable(modifyRadioParkingAvailable);

                databaseHelper.updateHike(hike);
                // Toast.makeText(this, "Updated: "+Toast.LENGTH_LONG).show();

                Intent listIntent = new Intent(ModifyHikeActivity.this, ListHikeActivity.class);
                setResult(RESULT_OK);
                finish();
            }
        });

//        btnSaveQualification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("User ID","*********"+id);
//                Intent intent=new Intent(ModifyUserActivity.this, AddQualificationActivity.class);
//                intent.putExtra("user_id",Integer.parseInt(id));
//                startActivity(intent);
//            }
//        });
//
//        btnQList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("User ID","*********"+id);
//                Intent intent=new Intent(ModifyUserActivity.this, QualificationListActivity.class);
//                intent.putExtra("user_id",Integer.parseInt(id));
//                startActivity(intent);
//            }
//        });



    }//on create
}//class
