package com.example.mhike;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddHikeActivity extends AppCompatActivity {
    private EditText editHikeDate;
    private EditText hikeNameEditText;
    private EditText hikeLocationEditText;
    private Spinner editDifficultySpinner;
    private EditText editLength;

    private EditText editDescription;
    private EditText editTrailCondition;
    private EditText editRecommendedGear;
    private RadioGroup editParking ;
    RadioButton selectedRadioButton;
    DatabaseHelper databaseHelper;

    String selectedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hike);

        ScrollView scrollView = findViewById(R.id.scrollView); // Replace with your ScrollView's ID
        scrollView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);


        editDifficultySpinner = findViewById(R.id.difficultySpinner);
        Button submitButton = findViewById(R.id.submitButton); // Replace with your button's ID
        hikeNameEditText = findViewById(R.id.editHikeName);
        hikeLocationEditText = findViewById(R.id.editHikeLocation);
        editHikeDate = findViewById(R.id.editHikeDate);
        editLength = findViewById(R.id.editHikeLength);
        editParking = findViewById(R.id.radioParkingAvailable);
        editDescription = findViewById(R.id.editDescription);
        editTrailCondition = findViewById(R.id.editTrailConditions);
        editRecommendedGear = findViewById(R.id.editRecommendedGears);
        int selectedRadioButtonId = editParking.getCheckedRadioButtonId();
        selectedRadioButton = findViewById(selectedRadioButtonId);

        // Define an array of difficulty levels
        String[] difficultyLevels = {"Easy", "Intermediate", "Difficult"};

        // Create an ArrayAdapter to populate the Spinner with difficulty levels
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, difficultyLevels);

        // Set the dropdown layout style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the Spinner
        editDifficultySpinner.setAdapter(adapter);

        editDifficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item from the spinner
                selectedValue = parentView.getItemAtPosition(position).toString();

                // Now you can use the selected value as needed
                // For example, you can log it or use it in your code
                Log.d("Selected Value", selectedValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here if you don't need to handle nothing selected
            }
        });

        editHikeDate = findViewById(R.id.editHikeDate);

        // Set the current date initially
        setCurrentDate();

        // Implement an OnClickListener to show the date picker
        editHikeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hikeName = hikeNameEditText.getText().toString().trim();
                String hikeLocation = hikeLocationEditText.getText().toString().trim();
                String hikeDate = editHikeDate.getText().toString().trim();
                String hikeLength = editLength.getText().toString().trim();
                String hikeDescription = editDescription.getText().toString().trim();
                String trailConditions = editTrailCondition.getText().toString().trim();
                String recommendedGears = editRecommendedGear.getText().toString().trim();

                if (hikeName.isEmpty()) {
                    hikeNameEditText.setError("Hike Name is required");
                } else {
                    hikeNameEditText.setError(null); // Clear the error message
                }

                if (hikeLocation.isEmpty()) {
                    hikeLocationEditText.setError("Hike Location is required");
                } else {
                    hikeLocationEditText.setError(null); // Clear the error message
                }

                if (hikeDate.isEmpty()) {
                    editHikeDate.setError("Hike Date is required");
                } else {
                    editHikeDate.setError(null); // Clear the error message
                }

                if (hikeLength.isEmpty()) {
                    editLength.setError("Hike Date is required");
                } else {
                    editLength.setError(null); // Clear the error message
                }

                if (!hikeName.isEmpty() && !hikeLocation.isEmpty() && !hikeDate.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddHikeActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.custom_dialog, null);
                    builder.setView(dialogView);

                    TextView confirmTextHikeName = dialogView.findViewById(R.id.confirmTextHikeName);
                    confirmTextHikeName.setText("Hike Name: " + hikeName);

                    TextView confirmTextHikeLocation = dialogView.findViewById(R.id.confirmTextHikeLocation);
                    confirmTextHikeLocation.setText("Hike Location: " + hikeLocation);

                    TextView confirmTextHikeDate = dialogView.findViewById(R.id.confirmTextHikeDate);
                    confirmTextHikeDate.setText("Hike Date: " + hikeDate);

                    TextView confirmTextHikeParking = dialogView.findViewById(R.id.confirmTextHikeParking);
                    confirmTextHikeParking.setText("Hike Date: " + selectedRadioButton.getText().toString());

                    TextView confirmTextHikeLength = dialogView.findViewById(R.id.confirmTextHikeLength);
                    confirmTextHikeLength.setText("Hike Length: " + hikeLength);

                    TextView confirmTextHikeDifficulty = dialogView.findViewById(R.id.confirmTextHikeDate);
                    confirmTextHikeDifficulty.setText("Difficulty Level: " + selectedValue);

                    if (!hikeDescription.isEmpty()){
                        TextView confirmTextHikeDescription = dialogView.findViewById(R.id.confirmTextHikeDescription);
                        confirmTextHikeDescription.setText("Hike Description: " + hikeDescription);
                    }

                    if (!hikeDescription.isEmpty()){
                        TextView confirmTextHikeTrailConditions = dialogView.findViewById(R.id.confirmTextHikeTrailConditions);
                        confirmTextHikeTrailConditions.setText("Trail Conditions: " + trailConditions);

                    }

                    if (!hikeDescription.isEmpty()){
                        TextView confirmTextHikeRecommendedGears = dialogView.findViewById(R.id.confirmTextHikeRecommendedGears);
                        confirmTextHikeRecommendedGears.setText("Recommended Gears: " + recommendedGears);
                    }

                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            addHike();
                            dialog.dismiss();
                            setResult(RESULT_OK);
                            finish();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Handle the cancel action
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }

            }
        });
    }

    private void addHike() {

        databaseHelper = new DatabaseHelper(this);

        String name = hikeNameEditText.getText().toString();
        String location = hikeLocationEditText.getText().toString();
        String date = editHikeDate.getText().toString();
        String parking = selectedRadioButton.getText().toString();
        Integer  length = Integer.valueOf(editLength.getText().toString());
        String difficultyLevel = editDifficultySpinner.getSelectedItem().toString();
        String description = editDescription.getText().toString();
        String trailCondition = editTrailCondition.getText().toString();
        String recommendedGear = editRecommendedGear.getText().toString();

        Hike hike=new Hike(name, location, date,parking,length,difficultyLevel,description,trailCondition,recommendedGear);

        long user_id = databaseHelper.saveHike(hike);
        Log.d("add","id"+user_id);

        Toast.makeText(this, "Added: "+user_id,Toast.LENGTH_LONG).show();
    }

    // Function to set the current date in the EditText
    private void setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        editHikeDate.setText(currentDate);
    }

    // Function to show the date picker dialog
    private void showDatePickerDialog() {
        // Use the current date as the default in the date picker
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Handle the selected date (e.g., update the EditText)
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                editHikeDate.setText(selectedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }
}

