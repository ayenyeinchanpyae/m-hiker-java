package com.example.mhike;

import static com.example.mhike.DatabaseHelper.HIKE_NAME;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    DatabaseHelper hikeDatabaseHelper, observationDatabaseHelper;
    ListView userListView;

    DatabaseHelper databaseHelper;
    private EditText searchEditText;
    private Button getLocationBtn;
    String[] from = new String[]{HIKE_NAME, DatabaseHelper.HIKE_LOCATION, DatabaseHelper.HIKE_DATE};
    int[] to = new int[]{R.id.textViewHikeName, R.id.textViewHikeLocation, R.id.textViewHikeDate};

    private static final int ADD_HIKE_REQUEST_CODE = 1;
    private FloatingActionButton addButton;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123; // Use any unique integer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hike);

        // Create a callback for the onBackPressed event
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {

            @Override
            public void handleOnBackPressed() {
                // Your custom back button handling logic
                Log.d("onBackPressed", "Back button pressed");
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    finish();
                }
            }
        };
        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);



        hikeDatabaseHelper = new DatabaseHelper(this);
        observationDatabaseHelper = new DatabaseHelper(this, "observationdb", 1);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_home) {
                    return true;
                } else if (itemId == R.id.menu_map) {
                    startActivity(new Intent(MainActivity.this, MapActivity.class));
                    return true;
                } else if (itemId == R.id.menu_observation) {
                    startActivity(new Intent(MainActivity.this, ActivityObservationList.class));
                    return true;
                } else if (itemId == R.id.menu_profile) {
                    startActivity(new Intent(MainActivity.this, ChartActivity.class));
                    return true;
                }

                return false;
            }

        });

        userListView = findViewById(R.id.userListView);
        addButton = findViewById(R.id.fabAdd);

        databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.fetchAllHikes();

        CustomCursorAdapter cursorAdapter = new CustomCursorAdapter(this, R.layout.hike_list_layout, cursor, from, to, 0);
        userListView.setAdapter(cursorAdapter);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click for the entire ListView item
                Log.d("user list view", "click");

                TextView textViewHikeName = view.findViewById(R.id.textViewHikeName);
                TextView textViewHikeLocation = view.findViewById(R.id.textViewHikeLocation);
                TextView textViewHikeDate = view.findViewById(R.id.textViewHikeDate);

                String name = textViewHikeName.getText().toString();
                String location = textViewHikeLocation.getText().toString();
                String date = textViewHikeDate.getText().toString();

                Intent intent = new Intent(MainActivity.this, HikeDetailActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("location", location);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });


        searchEditText = findViewById(R.id.searchEditText);
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddHikeActivity.class);
                startActivityForResult(intent, ADD_HIKE_REQUEST_CODE);
            }
        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_HIKE_REQUEST_CODE && resultCode == RESULT_OK) {
            refreshHikeList();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true; // Change this to true to indicate that the menu is handled
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.deleteAllBtn) {
            // Perform your action here
            // For example, show a toast
            Toast.makeText(this, "Menu item clicked", Toast.LENGTH_SHORT).show();
            showConfirmationDialog();
            return true; // Return true to indicate that the event has been handled
        }

        // Add more if statements for other menu items if needed

        return super.onOptionsItemSelected(item);
    }


    private void performSearch() {
        String searchTerm = searchEditText.getText().toString();
        Cursor cursor = databaseHelper.searchHikes(searchTerm);

        if (userListView.getAdapter() == null) {
            String[] columns = {DatabaseHelper.HIKE_NAME, DatabaseHelper.HIKE_LOCATION, DatabaseHelper.HIKE_DATE};
            int[] to = {R.id.textViewHikeName, R.id.textViewHikeLocation, R.id.textViewHikeDate};
            CustomCursorAdapter cursorAdapter = new CustomCursorAdapter(this, R.layout.hike_list_layout, cursor, columns, to, 0);
            userListView.setAdapter(cursorAdapter);
        } else {
            CustomCursorAdapter cursorAdapter = (CustomCursorAdapter) userListView.getAdapter();
            cursorAdapter.changeCursor(cursor);
            cursorAdapter.notifyDataSetChanged();
        }
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete all hike info?");

        // Add positive button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked "Yes." Perform your action here.
                hikeDatabaseHelper.deleteAllHikes();
                refreshHikeList();

                // For example, show a toast
                Toast.makeText(MainActivity.this, "Action confirmed", Toast.LENGTH_SHORT).show();
            }
        });

        // Add negative button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked "No." Perform any additional actions if needed.
                dialog.dismiss(); // Dismiss the dialog
            }
        });

        // Show the dialog
        builder.show();
    }
    private void refreshHikeList() {
        Cursor updatedCursor = databaseHelper.fetchAllHikes();
        CustomCursorAdapter cursorAdapter = (CustomCursorAdapter) userListView.getAdapter();
        cursorAdapter.changeCursor(updatedCursor);
        cursorAdapter.notifyDataSetChanged();
    }

    private void showDeleteConfirmationDialog(final int hikeId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // Use your activity or context here

        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this item?");

        // Add buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d("delete","id"+hikeId);
                // User clicked Yes button, handle the delete action
                performDeleteAction(hikeId);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked No button, do nothing
            }
        });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void performDeleteAction(int hikeId) {
        // Call your deleteHike method from the database helper
        DatabaseHelper dbHelper = new DatabaseHelper(this); // Replace 'this' with your actual activity or context
        dbHelper.deleteHike(hikeId);

        refreshHikeList();
        // Item deleted successfully, you can show a message or perform any other action
        Toast.makeText(this, "Item deleted successfully", Toast.LENGTH_SHORT).show();

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
                    // Log.d("ImageButton Click", "Clicked item ID: " + itemId);

                    // Create a PopupMenu and handle the click event
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.inflate(R.menu.hike_menu);

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Cursor cursor = getCursor();
                            long hikeId = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.HIKE_ID));
                            Log.d("ImageButton Click", "Clicked item ID: " + hikeId);
                            String hikeIdString = String.valueOf(hikeId);

                            int itemId = item.getItemId();
                            if (itemId == R.id.menu_edit) {

                               Intent intent = new Intent(MainActivity.this, ModifyHikeActivity.class);
                               intent.putExtra("id", hikeIdString);

                                // Start the EditActivity
                                startActivity(intent);
                                return true;
                            } else if (itemId == R.id.menu_delete) {
                                showDeleteConfirmationDialog(Integer.parseInt(hikeIdString));
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
}
