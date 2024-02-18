package com.example.mhike;

import static com.example.mhike.DatabaseHelper.HIKE_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListHikeActivity extends AppCompatActivity  {

    ListView userListView;

    DatabaseHelper databaseHelper;
    private EditText searchEditText;

    String[] from=new String[]{HIKE_NAME,DatabaseHelper.HIKE_LOCATION,DatabaseHelper.HIKE_DATE, String.valueOf(DatabaseHelper.HIKE_PARKING), String.valueOf(DatabaseHelper.HIKE_LENGTH), DatabaseHelper.HIKE_DESCRIPTION, DatabaseHelper.HIKE_TRAIL_CONDITION, DatabaseHelper.HIKE_RECOMMENDED_GEAR };
    //attribute names

    int[] to=new int[]{R.id.textViewHikeName, R.id.textViewHikeLocation,R.id.textViewHikeDate}; //layout id name
    private static final int ADD_HIKE_REQUEST_CODE = 1;
    private FloatingActionButton addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hike);

        userListView = findViewById(R.id.userListView);
        addButton = findViewById(R.id.fabAdd);

        databaseHelper = new DatabaseHelper(this);
        Cursor cursor =databaseHelper.fetchAllHikes();

        CursorAdapter cursorAdapter=new SimpleCursorAdapter(this,R.layout.hike_list_layout,cursor,from,to,0);
        cursorAdapter.notifyDataSetChanged();
        userListView.setAdapter(cursorAdapter);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ListUserActivity", "Selected item ID: " + id);
                TextView textViewHikeName= view.findViewById(R.id.textViewHikeName);
                TextView textViewHikeLocation= view.findViewById(R.id.textViewHikeLocation);
                TextView textViewHikeDate= view.findViewById(R.id.textViewHikeDate);

                String name=textViewHikeName.getText().toString();
                String location=textViewHikeLocation.getText().toString();
                String date=textViewHikeDate.getText().toString();

                Intent intent = new Intent(ListHikeActivity.this, HikeDetailActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("location",location);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });

        // Initialize the EditText
        searchEditText = findViewById(R.id.searchEditText);

        // Set the editor action listener
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    // Handle search when Enter key is pressed
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        // Set OnClickListener for the button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("add", "clicked");
                Intent intent = new Intent(ListHikeActivity.this, AddHikeActivity.class);
                startActivityForResult(intent, ADD_HIKE_REQUEST_CODE);
            }
        });

    }//end of onCreate
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_HIKE_REQUEST_CODE && resultCode == RESULT_OK) {
            // Handle the result from AddUserActivity
            refreshHikeList();
        }
    }
    private void performSearch() {
        String searchTerm = searchEditText.getText().toString();

        Cursor cursor = databaseHelper.searchHikes(searchTerm);

        ListView userListView = findViewById(R.id.userListView); // Replace with your ListView ID

        if (userListView.getAdapter() == null) {
            Log.d("cursor","null");
            // If the adapter is not set, create a new one
            String[] columns = {DatabaseHelper.HIKE_NAME, DatabaseHelper.HIKE_LOCATION, DatabaseHelper.HIKE_DATE};
            int[] to = {R.id.textViewHikeName, R.id.textViewHikeLocation, R.id.textViewHikeDate};

            CursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.hike_list_layout, cursor, columns, to, 0);

            userListView.setAdapter(cursorAdapter);
        } else {
            Log.d("cursor","not null");
            // If the adapter is already set, update the Cursor
            CursorAdapter cursorAdapter = (CursorAdapter) userListView.getAdapter();
            cursorAdapter.changeCursor(cursor);

            // Notify the adapter that the data has changed
            cursorAdapter.notifyDataSetChanged();
        }

        // Close the cursor after it has been used
        // cursor.close();

    }
//    public void showOptionsMenu(View view) {
//        ImageView optionsMenu = (ImageView) view;
//        PopupMenu popupMenu = new PopupMenu(this, optionsMenu);
//        popupMenu.getMenuInflater().inflate(R.menu.hike_menu, popupMenu.getMenu());
//
//        // Set item click listener
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                int itemId = item.getItemId();
//
//                if (itemId == R.id.addHikeBtn) {
//                    // Toast.makeText(ListUserActivity.this, "Add action clicked", Toast.LENGTH_SHORT).show();
//
//                        Intent intent = new Intent(ListUserActivity.this, AddUserActivity.class);
//                        startActivityForResult(intent, ADD_HIKE_REQUEST_CODE);
//
//                    return true;
//                } else if (itemId == R.id.menu_delete_all) {
//                    Toast.makeText(ListUserActivity.this, "Add action clicked", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        // Show the popup menu
//        popupMenu.show();
//    }

    private void refreshHikeList() {
        // Implement your logic to fetch the latest data from your data source
        // For example, you might fetch the updated list of hikes from a database or a network request
        Cursor updatedCursor = databaseHelper.fetchAllHikes();

        // Update the Cursor in your CursorAdapter
        CursorAdapter cursorAdapter = (CursorAdapter) userListView.getAdapter();
        cursorAdapter.changeCursor(updatedCursor);

        // Notify the adapter that the data has changed
        cursorAdapter.notifyDataSetChanged();
    }

}//end of the class
