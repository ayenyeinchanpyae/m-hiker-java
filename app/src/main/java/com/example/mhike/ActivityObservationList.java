package com.example.mhike;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ActivityObservationList extends AppCompatActivity {
    ListView observationListView;
    DatabaseHelper observationDatabaseHelper;
    private FloatingActionButton addButton;
    private static final int ADD_OBSERVATION_REQUEST_CODE = 22;

    String[] from=new String[]{DatabaseHelper.COLUMN_OBSERVATION,DatabaseHelper.COLUMN_ADDITIONAL_COMMENTS};
    int[] to=new int[]{R.id.textViewObservation, R.id.textViewComment};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_list);

        observationListView = findViewById(R.id.observationListView);

        Spinner spinnerHike = findViewById(R.id.listSpinnerHike);
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
        spinnerHike.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected hike ID
                long selectedHikeId = id;

                // Use the selected hike ID to fetch observations
                Cursor observationCursor = observationDatabaseHelper.getObservationsByHikeId(selectedHikeId);

                if (observationCursor != null && observationCursor.getCount() > 0) {
                    Log.d("ObservationList", "Observations found for Hike ID: " + selectedHikeId);
                    CursorAdapter cursorAdapter = new CustomCursorAdapter(ActivityObservationList.this, R.layout.observation_list_layout, observationCursor, from, to, 0);
                    observationListView.setAdapter(cursorAdapter);
                } else {
                    Log.d("ObservationList", "No observations found for Hike ID: " + selectedHikeId);
                    observationListView.setAdapter(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });


        observationDatabaseHelper = new DatabaseHelper(this, "observationdb", 1);
        Cursor cursor =observationDatabaseHelper.fetchAllObservations();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    Log.d("CursorData", cursor.getColumnName(i) + ": " + cursor.getString(i));
                }
                Log.d("CursorData", "------------");
            }
            cursor.moveToFirst(); // Reset cursor position for further use
        }

        if (cursor != null && cursor.getCount() > 0) {
            Log.d("ObservationList", "Cursor is not null or empty.");
            CursorAdapter  cursorAdapter = new CustomCursorAdapter(this, R.layout.observation_list_layout, cursor, from, to, 0);
            observationListView.setAdapter(cursorAdapter);

        } else {
            Log.d("ObservationList", "Cursor is null or empty.");
        }



        addButton = findViewById(R.id.observationAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("add observation list","add");
                // Handle the button click here
                // For example, start a new activity
                Intent intent = new Intent(ActivityObservationList.this, ActivityAddObservation.class);
                startActivityForResult(intent, ADD_OBSERVATION_REQUEST_CODE);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_OBSERVATION_REQUEST_CODE && resultCode == RESULT_OK) {
            // The observation was added successfully, refresh the list
            refreshObservationList();
        }
    }

    private void refreshObservationList() {
        // Fetch the updated cursor from the database
        Cursor updatedCursor = observationDatabaseHelper.fetchAllObservations();

        if (updatedCursor != null && updatedCursor.getCount() > 0) {
            // Cursor is not null and not empty
            Log.d("ObservationList", "refreshObservationList Cursor is not null or empty.");

            // Get the adapter and update its cursor
            CursorAdapter cursorAdapter = (CursorAdapter) observationListView.getAdapter();
            cursorAdapter.changeCursor(updatedCursor);
            cursorAdapter.notifyDataSetChanged();
        } else {
            // Cursor is null or empty
            Log.d("ObservationList", "Cursor is null or empty.");

            // If you want to clear the list when there are no observations, you can set the adapter to null
            observationListView.setAdapter(null);
        }
    }


    private class CustomCursorAdapter extends SimpleCursorAdapter {

        public CustomCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            ImageButton iconButton = view.findViewById(R.id.iconButton);

            // Declare position as final
            final int finalPosition = position;

            iconButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long itemId = getItemId(finalPosition);
                    Log.d("ImageButton Click", "Clicked item ID: " + itemId);

                    // Create a PopupMenu and handle the click event
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.inflate(R.menu.hike_menu);

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int itemId = item.getItemId();
                            if (itemId == R.id.menu_edit) {
                                Cursor cursor = getCursor();
                                long observationId = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                                Log.d("ImageButton Click", "Clicked item ID: " + observationId);


                                Intent intent = new Intent(ActivityObservationList.this, EditObservationActivity.class);
                                intent.putExtra("observationId", observationId);

                                // Start the EditActivity
                                startActivity(intent);
                                return true;
                            } else if (itemId == R.id.menu_delete) {
                                // showDeleteConfirmationPopup(observationId);
                                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityObservationList.this);
                                builder.setTitle("Delete Observation");
                                builder.setMessage("Are you sure you want to delete this observation?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // Perform the deletion action here
                                        Cursor cursor = getCursor();
                                        long observationId = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                                        Boolean isDeleted = observationDatabaseHelper.deleteObservation(observationId);
                                        Log.d("delete", "success"+isDeleted);
                                        // Notify the activity about the successful deletion
                                        onDeleteSuccess();
                                    }
                                });
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // Dismiss the dialog, no action needed
                                    }
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                return true;
                            } else {
                                return false;
                            }
                        }
                    });

                    popupMenu.show();
                }
            });

            return view;
        }

    }
    private void showDeleteConfirmationPopup(final long observationId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Observation");
        builder.setMessage("Are you sure you want to delete this observation?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Perform the deletion action here
                Boolean isDeleted = observationDatabaseHelper.deleteObservation(observationId);
                Log.d("delete", "success"+observationId);
                // Notify the activity about the successful deletion
                //onDeleteSuccess();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Dismiss the dialog, no action needed
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void onDeleteSuccess() {
        // Fetch the updated cursor after deletion
        Cursor updatedCursor = observationDatabaseHelper.fetchAllObservations();

        // Update the adapter with the new cursor
        CursorAdapter cursorAdapter = (CursorAdapter) observationListView.getAdapter();
        cursorAdapter.changeCursor(updatedCursor);
        cursorAdapter.notifyDataSetChanged();
    }

    }
