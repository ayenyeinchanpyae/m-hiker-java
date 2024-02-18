package com.example.mhike;

// HikeDetailActivity.java
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import static com.example.mhike.DatabaseHelper.HIKE_DESCRIPTION;
import static com.example.mhike.DatabaseHelper.HIKE_DIFFICULTY_LEVEL;
import static com.example.mhike.DatabaseHelper.HIKE_LENGTH;
import static com.example.mhike.DatabaseHelper.HIKE_PARKING;
import static com.example.mhike.DatabaseHelper.HIKE_RECOMMENDED_GEAR;
import static com.example.mhike.DatabaseHelper.HIKE_TRAIL_CONDITION;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
public class HikeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_info_fragment);






    }
}


//
//public class HikeDetailActivity extends AppCompatActivity {
//
//    protected void onCreate(Bundle savedInstanceState) {
//        TextView hikeName, hikeLocation,hikeDate, hikeParking, hikeLength, hikeDifficulity,hikeDescription, hikeTrailCondition, hikeRecommendedGear;
//        String hikeId, name, date, location, length, description, trailConditions, recommendedGears, difficulityLvl, parking;
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_hike_info); // Set the content view of your current activity
//
//        hikeName = findViewById(R.id.hikeName);
//        hikeLocation = findViewById(R.id.hikeLocation);
//        hikeDate = findViewById(R.id.hikeDate);
//        hikeLength = findViewById(R.id.hikeLength);
//        hikeParking = findViewById(R.id.hikeParking);
//        hikeDifficulity = findViewById(R.id.hikeDifficulity);
//        hikeDescription = findViewById(R.id.hikeDescription);
//        hikeTrailCondition = findViewById(R.id.hikeTrailCondition);
//        hikeRecommendedGear = findViewById(R.id.hikeRecommendedGear);
//
//        ImageButton hikeEditBtn = findViewById(R.id.hikeEditBtn);
//        ImageButton hikeDeleteBtn = findViewById(R.id.hikeDeleteBtn);
//        Intent intent = getIntent();
//         hikeId = String.valueOf(intent.getLongExtra("id", -1));
//         name = intent.getExtras().getString("name");
//         location = intent.getExtras().getString("location");
//         date = intent.getExtras().getString("date");
//
//        DatabaseHelper databaseHelper = new DatabaseHelper(this);
//
//        Cursor cursor = databaseHelper.getRowById(Long.parseLong(hikeId));
//
//        if (cursor != null && cursor.moveToFirst()) {
//            // Retrieve data from the cursor and populate your UI elements
//
//            length = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_LENGTH));
//            description = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_DESCRIPTION));
//            trailConditions = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_TRAIL_CONDITION));
//            recommendedGears = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_RECOMMENDED_GEAR));
//            difficulityLvl = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_DIFFICULTY_LEVEL));
//            parking = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_PARKING));
//
//            cursor.close();
//        }
//
//
//        hikeName.setText(name);
//        hikeLocation.setText(location);
//        hikeDate.setText(date);
//        hikeParking.setText(date);
//        hikeLength.setText(name);
//        hikeDifficulity.setText(name);
//        hikeDescription.setText(name);
//        hikeTrailCondition.setText(name);
//        hikeRecommendedGear.setText(name);
//
//        hikeEditBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HikeDetailActivity.this, ModifyUserActivity.class);
//                intent.putExtra("id",hikeId);
//                intent.putExtra("name",name);
//                intent.putExtra("location",location);
//                intent.putExtra("date",date);
//                startActivity(intent);
//            }
//        });
//
//        hikeDeleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(HikeDetailActivity.this);
//                builder.setMessage("Are you sure you want to delete?")
//                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                Log.d("MyTag",hikeId);
//                                databaseHelper.deleteHike(Integer.parseInt(hikeId));
//                                Intent goToCertainPage = new Intent(HikeDetailActivity.this, MainActivity.class);
//                                startActivity(goToCertainPage);
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                // User canceled the operation, do nothing or dismiss the dialog
//                                dialog.dismiss();
//                            }
//                        });
//                // Create and show the AlertDialog
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });
//
//    }
//}
