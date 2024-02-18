package com.example.mhike;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "hikedb";
    private static final String TABLE_NAME = "hike";
    public static final String HIKE_ID = "_id"; //cursor adapter//primary key
    public static final String HIKE_NAME = "name";
    public static final String HIKE_LOCATION = "location";
    public static final String HIKE_DATE = "date";
    public static final String HIKE_PARKING = "parking";
    public static final String HIKE_LENGTH = "length";
    public static final String HIKE_DIFFICULTY_LEVEL = "difficultyLevel";
    public static final String HIKE_DESCRIPTION = "description";
    public static final String HIKE_TRAIL_CONDITION = "trailCondition";
    public static final String HIKE_RECOMMENDED_GEAR = "recommendedGear";

    private static final String QUALIFICATION_TABLE_NAME = "qualification";

    public static final String Q_ID = "_id"; //cursor adapter//primary key

    public static final String Q_TITLE = "title";

    public static final String Q_YEAR = "year";

    public static final String Q_USER_ID = "user_id"; //foreign key

    public static final String TABLE_OBSERVATIONS = "observations";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_OBSERVATION = "observation";
    public static final String COLUMN_TIME_OF_OBSERVATION = "time_of_observation";
    public static final String COLUMN_ADDITIONAL_COMMENTS = "additional_comments";
    //public static final String COLUMN_IMAGE_PATH = "image_path";

    private static final String DATABASE_NAME = "observations.db";
    private static final int DATABASE_VERSION = 1;
    public static final String COLUMN_HIKE_ID = "hike_id";

    private static final String DBNAME_HIKE = "hikedb";
    private static final String DBNAME_OBSERVATION = "observationdb";
    private static final int VERSION_HIKE = 2;
    private static final int VERSION_OBSERVATION = 1;

    private SQLiteDatabase database;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DBNAME_HIKE, null, VERSION_HIKE);
        database = getWritableDatabase();
    }

    // Constructor for the second database
    public DatabaseHelper(Context context,String dbName, int databaseVersion) {
        super(context, dbName, null, databaseVersion);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_HIKE = "CREATE TABLE " + TABLE_NAME + " (" +
                HIKE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HIKE_NAME + " TEXT, " +
                HIKE_LOCATION + " TEXT, " +
                HIKE_DATE + " TEXT, " +
                HIKE_PARKING + " TEXT, " + // Use TEXT for 'Yes' or 'No'
                HIKE_LENGTH + " INTEGER, " + // Integer data type for length
                HIKE_DIFFICULTY_LEVEL + " TEXT, " +
                HIKE_DESCRIPTION + " TEXT, " +
                HIKE_TRAIL_CONDITION + " TEXT, " +
                HIKE_RECOMMENDED_GEAR + " TEXT)";



        String CREATE_TABLE_OBSERVATIONS =
                "CREATE TABLE IF NOT EXISTS " + TABLE_OBSERVATIONS + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_HIKE_ID + " INTEGER, " +
                        COLUMN_OBSERVATION + " TEXT, " +
                        COLUMN_TIME_OF_OBSERVATION + " INTEGER, " +
                        COLUMN_ADDITIONAL_COMMENTS + " TEXT);";



        if (getDatabaseName().equals(DBNAME_HIKE)) {
            // Create tables for the "hike" database
            db.execSQL(CREATE_TABLE_HIKE);
        } else if (getDatabaseName().equals(DBNAME_OBSERVATION)) {
            // Create tables for the "observation" database
            db.execSQL(CREATE_TABLE_OBSERVATIONS);
        }

        Log.d("SQL_DEBUG", "Insert Statement: " + CREATE_TABLE_OBSERVATIONS);


    }//onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable1 = "drop table if exists "+TABLE_NAME;
        db.execSQL(dropTable1);
        String dropTable2 = "drop table if exists "+QUALIFICATION_TABLE_NAME;
        db.execSQL(dropTable2);
        onCreate(db);
    }//onUpgrade

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;"); //foreign key
    }

    public long saveHike(Hike hike){

        ContentValues values = new ContentValues();
        values.put(HIKE_NAME,hike.getHikeName()); //name
        values.put(HIKE_LOCATION, hike.getLocation()); //location
        values.put(HIKE_DATE, hike.getDate()); //date
        values.put(HIKE_DIFFICULTY_LEVEL, hike.getDifficultyLevel()); //a
        values.put(String.valueOf(HIKE_LENGTH), hike.getHikeLength()); //date
        values.put(HIKE_PARKING, hike.isParkingAvailable()); //date
        values.put(HIKE_DESCRIPTION, hike.getDescription()); //date
        values.put(HIKE_TRAIL_CONDITION, hike.getTrailConditions()); //date
        values.put(HIKE_RECOMMENDED_GEAR, hike.getRecommendedGear()); //date

        long hike_id = database.insertOrThrow(TABLE_NAME,null,values);

        return hike_id;
    }//end of saveHike

    // Search for hikes based on a keyword
    public Cursor searchHikes(String keyword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {HIKE_ID + " as _id", HIKE_NAME,
                HIKE_LOCATION,
                HIKE_DATE,
                HIKE_DIFFICULTY_LEVEL,
                HIKE_LENGTH,
                HIKE_PARKING,
                HIKE_DESCRIPTION,
                HIKE_TRAIL_CONDITION,
                HIKE_RECOMMENDED_GEAR};
        String selection = HIKE_NAME + " LIKE ?";
        String[] selectionArgs = {"%" + keyword + "%"};

        return db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
    }

    public List<String> getAllHikeLengths() {
        List<String> hikeLengths = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{HIKE_LENGTH},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String hikeLength = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_LENGTH));
                    hikeLengths.add(hikeLength);
                }
            } finally {
                cursor.close();
            }
        }

        return hikeLengths;
    }

    public List<String> getAllHikeNames() {
        List<String> hikeNames = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{HIKE_NAME},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String hikeName = cursor.getString(cursor.getColumnIndexOrThrow(HIKE_NAME));
                    hikeNames.add(hikeName);
                }
            } finally {
                cursor.close();
            }
        }

        return hikeNames;
    }
    public Cursor getRowById(long id) {
        SQLiteDatabase database = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {
                HIKE_NAME,
                HIKE_LOCATION,
                HIKE_DATE,
                HIKE_DIFFICULTY_LEVEL,
                HIKE_LENGTH,
                HIKE_PARKING,
                HIKE_DESCRIPTION,
                HIKE_TRAIL_CONDITION,
                HIKE_RECOMMENDED_GEAR
        };

        // Specify the selection criteria based on the ID
        String selection = HIKE_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        // Execute the query to retrieve the row
        Cursor cursor = database.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        return cursor;
    }
    public void deleteAllHikes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
    public Cursor getObservationsByHikeId(long hikeId) {
        SQLiteDatabase database = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {
                COLUMN_ID,
                COLUMN_OBSERVATION,
                COLUMN_TIME_OF_OBSERVATION,
                COLUMN_ADDITIONAL_COMMENTS,

        };

        // Specify the selection criteria based on the hikeId
        String selection = COLUMN_HIKE_ID + " = ?";
        String[] selectionArgs = { String.valueOf(hikeId) };

        // Execute the query to retrieve the observations
        return database.query(
                TABLE_OBSERVATIONS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    public Cursor getObservationById(long observationId) {
        SQLiteDatabase database = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {
                COLUMN_ID,
                COLUMN_OBSERVATION,
                COLUMN_TIME_OF_OBSERVATION,
                COLUMN_ADDITIONAL_COMMENTS,

        };

        // Specify the selection criteria based on the observationId
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(observationId) };

        // Execute the query to retrieve the observation
        return database.query(
                TABLE_OBSERVATIONS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    public long saveObservation(Observation observation) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_HIKE_ID, observation.getHikeId());
        values.put(COLUMN_OBSERVATION, observation.getObservation());
        values.put(COLUMN_TIME_OF_OBSERVATION, observation.getTimeOfObservation().getTime()); // Current time
        values.put(COLUMN_ADDITIONAL_COMMENTS, observation.getAdditionalComments());
        //values.put(COLUMN_IMAGE_PATH, observation.getImagePath());

        long rowId = db.insert(TABLE_OBSERVATIONS, null, values);

        db.close();

        return rowId;
    }




    public ArrayList<Hike> getAllHikes() {
        database = getReadableDatabase();

        Cursor results = database.query(
                TABLE_NAME,
                new String[]{HIKE_ID, HIKE_NAME, HIKE_LOCATION, HIKE_DATE, HIKE_DIFFICULTY_LEVEL, HIKE_LENGTH, HIKE_PARKING, HIKE_DESCRIPTION, HIKE_TRAIL_CONDITION, HIKE_RECOMMENDED_GEAR},
                null,
                null,
                null,
                null,
                HIKE_NAME
        );

        ArrayList<Hike> hikeList = new ArrayList<>();

        results.moveToFirst();

        while (!results.isAfterLast()) {
            long id = results.getLong(0);
            String hikeName = results.getString(1);
            String location = results.getString(2);
            String date = results.getString(3);
            String difficultyLevel = results.getString(4);
            int hikeLength = results.getInt(5);
            String parkingAvailable = results.getString(6);
            String description = results.getString(7);
            String trailConditions = results.getString(8);
            String recommendedGear = results.getString(9);

            Hike hike = new Hike(id, hikeName, location, date, parkingAvailable, hikeLength, difficultyLevel, description, trailConditions, recommendedGear);
            hikeList.add(hike);

            results.moveToNext();
        }

        results.close();

        return hikeList;
    }


    public ArrayList<Observation> getAllObservations() {
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor results = db.query(
                TABLE_OBSERVATIONS,
                new String[]{COLUMN_ID, COLUMN_OBSERVATION, COLUMN_TIME_OF_OBSERVATION, COLUMN_ADDITIONAL_COMMENTS},
                null,
                null,
                null,
                null,
                COLUMN_TIME_OF_OBSERVATION
        );

        ArrayList<Observation> observationList = new ArrayList<>();

        results.moveToFirst();

        while (!results.isAfterLast()) {
            long id = results.getLong(0);
            String observation = results.getString(1);
            String timeOfObservationString = results.getString(2);
            String additionalComments = results.getString(3);
            //String imagePath = results.getString(4);

            // Convert the string representation of the date to a Date object
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date timeOfObservation;
            try {
                timeOfObservation = dateFormat.parse(timeOfObservationString);
            } catch (ParseException e) {
                // Handle the parsing exception as needed
                e.printStackTrace();
                timeOfObservation = new Date(); // Default to the current date if parsing fails
            }

            Observation observationObject = new Observation(id, observation, timeOfObservation, additionalComments);
            observationList.add(observationObject);

            results.moveToNext();
        }

        results.close();

        return observationList;
    }

    public void deleteHike(int user_id){

        database.delete(TABLE_NAME,HIKE_ID+"=?",new String[]{String.valueOf(user_id)});
    }

//    public void deleteObservation(long observation_id){
//
//        database.delete(TABLE_OBSERVATIONS,COLUMN_ID+"=?",new String[]{String.valueOf(observation_id)});
//    }

//    public boolean deleteObservation(long observation_id) {
//        try {
//            int rowsDeleted = database.delete(TABLE_OBSERVATIONS, COLUMN_ID + "=?", new String[]{String.valueOf(observation_id)});
//            boolean isDeleted = rowsDeleted > 0;
//            return isDeleted;
//        } catch (SQLiteException e) {
//            // Log or handle the exception
//            Log.e("Delete Observation", "Error deleting observation", e);
//            e.printStackTrace();
//            return false; // Return false to indicate deletion failure due to an exception
//        }
//    }
//

    public boolean deleteObservation(long observation_id) {
        try {
            String[] whereArgs = new String[]{String.valueOf(observation_id)};

            // Log the where clause for debugging
            Log.d("Delete Observation", "Where Clause: " + COLUMN_ID + " = " + observation_id);

            int rowsDeleted = database.delete(TABLE_OBSERVATIONS, COLUMN_ID + "=?", whereArgs);

            // Check if the deletion was successful
            return rowsDeleted > 0;
        } catch (Exception e) {
            // Log the exception for debugging
            Log.e("Delete Observation", "Error deleting observation", e);
            return false;
        }
    }


    //update
    public void updateHike(Hike hike){

        ContentValues values = new ContentValues();
        values.put(HIKE_NAME,hike.getHikeName());
        values.put(HIKE_LOCATION,hike.getLocation());
        values.put(HIKE_DATE,hike.getDate());
        values.put(HIKE_DIFFICULTY_LEVEL,hike.getDifficultyLevel());
        values.put(HIKE_LENGTH,hike.getHikeLength());
        values.put(HIKE_DESCRIPTION,hike.getDescription());
        values.put(HIKE_PARKING,hike.isParkingAvailable());
        values.put(HIKE_TRAIL_CONDITION,hike.getTrailConditions());
        values.put(HIKE_RECOMMENDED_GEAR,hike.getRecommendedGear());


        database.update(TABLE_NAME,values,HIKE_ID+"=?",
                new String[]{String.valueOf(hike.getId())});
    }

    public void updateObservation(Observation observation) {
        ContentValues values = new ContentValues();

        // Adding observation and additional comments
        values.put(COLUMN_OBSERVATION, observation.getObservation());
        values.put(COLUMN_ADDITIONAL_COMMENTS, observation.getAdditionalComments());

        database.update(TABLE_OBSERVATIONS, values, COLUMN_ID + "=?",
                new String[]{String.valueOf(observation.getId())});

    }

    public Cursor fetchAllHikes(){
        database = getReadableDatabase();

        String[] columns = new String[]{HIKE_ID,HIKE_NAME,HIKE_LOCATION,HIKE_DATE, String.valueOf(HIKE_PARKING), String.valueOf(HIKE_LENGTH),HIKE_DIFFICULTY_LEVEL, HIKE_DESCRIPTION, HIKE_TRAIL_CONDITION, HIKE_RECOMMENDED_GEAR};

        Cursor cursor = database.query(TABLE_NAME,columns,null,null,null,null,null);

        if(cursor!=null) cursor.moveToFirst();

        return cursor;
    }

    public Cursor fetchAllObservations() {
        database = getReadableDatabase();

        String[] columns = new String[]{COLUMN_ID + " as _id", COLUMN_OBSERVATION, COLUMN_TIME_OF_OBSERVATION, COLUMN_ADDITIONAL_COMMENTS};

        Cursor cursor = database.query(TABLE_OBSERVATIONS, columns, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

}//end of the class
