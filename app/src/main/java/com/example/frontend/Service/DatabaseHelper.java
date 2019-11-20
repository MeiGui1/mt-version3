package com.example.frontend.Service;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.frontend.Models.Patient;
import com.example.frontend.Models.User;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.android.ContextHolder;
import org.sqldroid.DroidDataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Pacons_DB";

    // Table name: User.
    private static final String TABLE_USER = "User";
    private static final String COLUMN_USERID ="id";
    private static final String COLUMN_USERNAME ="username";
    private static final String COLUMN_USERPASSWORD = "password";

    // Table name: Patient.
    private static final String TABLE_PATIENT = "Patient";
    private static final String COLUMN_PATIENTID ="id";
    private static final String COLUMN_PATIENT_SHORTNAME ="shortname";
    private static final String COLUMN_PATIENT_GENDER = "gender";
    private Context c;

    public DatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        c = context;
    }

    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Script.
        String script = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USERID + " INTEGER PRIMARY KEY," + COLUMN_USERNAME + " TEXT,"
                + COLUMN_USERPASSWORD + " TEXT" + ")";
        // Execute Script.
        db.execSQL(script);

        db.execSQL("CREATE TABLE Patient (\n" +
                "    id INTEGER PRIMARY KEY,\n" +
                "    shortname text NOT NULL,\n" +
                "    gender text NOT NULL\n" +
                ");");

        try {
                insertFromFile("V1__PatientTable.sql",db);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }



    // If User table has no data
    // default, Insert 2 records.
    public void createDefaultUsersIfNeed()  {
        int count = this.getUsersCount();
        if(count ==0 ) {
            User user1 = new User("admin",
                    "zzm");
            User user2 = new User("de",
                    "zzm");
            User user3 = new User("nl",
                    "zzm");
            User user4 = new User("aw",
                    "zzm");
            this.addUser(user1);
            this.addUser(user2);
            this.addUser(user3);
            this.addUser(user4);
        }
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_USERPASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USER, null, values);

        // Closing database connection
        db.close();
    }

    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] { COLUMN_USERID,
                        COLUMN_USERNAME, COLUMN_USERPASSWORD }, COLUMN_USERID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return user
        return user;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                // Adding user to list
                userList.add(user);
            } while (cursor.moveToNext());
        }

        // return user list
        return userList;
    }

    public int getUsersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_USERPASSWORD, user.getPassword());

        // updating row
        return db.update(TABLE_USER, values, COLUMN_USERID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, COLUMN_USERID + " = ?",
                new String[] { String.valueOf(user.getId()) });
        db.close();
    }


    public void addPatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PATIENT_SHORTNAME, patient.getShortname());
        values.put(COLUMN_PATIENT_GENDER, patient.getGender());

        // Inserting Row
        db.insert(TABLE_PATIENT, null, values);

        // Closing database connection
        db.close();
    }

    public Patient getPatient(int patientId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PATIENT, new String[] { COLUMN_PATIENTID,
                        COLUMN_PATIENT_SHORTNAME, COLUMN_PATIENT_GENDER }, COLUMN_PATIENTID + "=?",
                new String[] { String.valueOf(patientId) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Patient patient = new Patient(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return patient
        return patient;
    }

    public List<Patient> getAllPatients() {
        List<Patient> patientList = new ArrayList<Patient>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PATIENT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Patient patient = new Patient();
                patient.setId(Integer.parseInt(cursor.getString(0)));
                patient.setShortname(cursor.getString(1));
                patient.setGender(cursor.getString(2));
                // Adding patient to list
                patientList.add(patient);
            } while (cursor.moveToNext());
        }

        // return patient list
        return patientList;
    }

    public int updatePatient(int patientId, Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PATIENT_SHORTNAME, patient.getShortname());
        values.put(COLUMN_PATIENT_GENDER, patient.getGender());

        // updating row
        return db.update(TABLE_PATIENT, values, COLUMN_PATIENTID + " = ?",
                new String[]{String.valueOf(patientId)});
    }

    public void deletePatient(int patientId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PATIENT, COLUMN_PATIENTID + " = ?",
                new String[] { String.valueOf(patientId) });
        db.close();
    }

    public int selectLastPatientId(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PATIENT, new String[] {COLUMN_PATIENTID,
                COLUMN_PATIENT_SHORTNAME, COLUMN_PATIENT_GENDER },null, null, null, null, null);
        cursor.moveToLast();
        int lastId = Integer.parseInt(cursor.getString(0));
        return lastId;
    }
    public void insertFromFile(String fileName, SQLiteDatabase db) throws IOException {
        // Open the resource
        InputStream insertsStream = c.getAssets().open(fileName);
        BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));

        // Iterate through lines (assuming each insert has its own line and theres no other stuff)
        while (insertReader.ready()) {
            String insertStmt = insertReader.readLine();
            db.execSQL(insertStmt);
        }
        insertReader.close();
    }
}
