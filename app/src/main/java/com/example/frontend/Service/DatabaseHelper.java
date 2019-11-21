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

    private Context c;

    public DatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        c = context;
    }

    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {

        createTables(db);

        try {
            insertFromFile("V1__PatientTable.sql",db);
            insertFromFile("V2__DrugTables.sql",db);
            insertFromFile("V3__DiagnosisTables.sql",db);
            insertFromFile("V4__ExerciseTables.sql",db);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Patient");
        db.execSQL("DROP TABLE IF EXISTS DrugType");
        db.execSQL("DROP TABLE IF EXISTS PatientDrug");
        db.execSQL("DROP TABLE IF EXISTS DiagnosisType");
        db.execSQL("DROP TABLE IF EXISTS PatientDiagnosis");
        db.execSQL("DROP TABLE IF EXISTS ExerciseType");
        db.execSQL("DROP TABLE IF EXISTS PatientExercise");
        db.execSQL("DROP TABLE IF EXISTS ExercisePhoto");
        db.execSQL("DROP TABLE IF EXISTS Note");
        db.execSQL("DROP TABLE IF EXISTS PsychoSocialBefore");
        db.execSQL("DROP TABLE IF EXISTS PsychoSocialAfter");
        db.execSQL("DROP TABLE IF EXISTS ImprovementReason");
        db.execSQL("DROP TABLE IF EXISTS PatientImage");
        db.execSQL("DROP TABLE IF EXISTS PatientVideo");
        db.execSQL("DROP TABLE IF EXISTS PatientDocument");
        db.execSQL("DROP TABLE IF EXISTS WebsiteType");
        db.execSQL("DROP TABLE IF EXISTS PatientWebsite");
        db.execSQL("DROP TABLE IF EXISTS PainBeginning");
        db.execSQL("DROP TABLE IF EXISTS PainCurrent");

        // Create tables again
        onCreate(db);
    }

    public void createTables(SQLiteDatabase db){
        //User Table
        db.execSQL("CREATE TABLE User (" +
                "id INTEGER PRIMARY KEY, " +
                "username text, " +
                "password TEXT)");

        //Patient Table
        db.execSQL("CREATE TABLE Patient ( " +
                "    id INTEGER PRIMARY KEY, " +
                "    shortname text NOT NULL, " +
                "    gender text NOT NULL " +
                ")");

        //Drug Tables
        db.execSQL("CREATE TABLE DrugType ( " +
                "    id INTEGER PRIMARY KEY, " +
                "    name text NOT NULL, " +
                "    description text " +
                ")");
        db.execSQL("CREATE TABLE PatientDrug ( " +
                "    patient_id int NOT NULL, " +
                "    drugtype_id int NOT NULL, " +
                "    amount text, " +
                "    dosis char(4), " +
                "    FOREIGN KEY (patient_id) REFERENCES Patient(id) ON DELETE CASCADE, " +
                "    FOREIGN KEY (drugtype_id) REFERENCES DrugType(id) ON DELETE CASCADE, " +
                "    UNIQUE (patient_id, drugtype_id) " +
                ")");

        //Diagnosis Tables
        db.execSQL("CREATE TABLE DiagnosisType ( " +
                "    id INTEGER PRIMARY KEY, " +
                "    name text NOT NULL, " +
                "    type text NOT NULL, " +
                "    description text " +
                ")");
        db.execSQL("CREATE TABLE PatientDiagnosis ( " +
                "    patient_id int NOT NULL, " +
                "    diagnosistype_id int NOT NULL, " +
                "    comment text, " +
                "    FOREIGN KEY (patient_id) REFERENCES Patient(id) ON DELETE CASCADE, " +
                "    FOREIGN KEY (diagnosistype_id) REFERENCES DiagnosisType(id) ON DELETE CASCADE, " +
                "    UNIQUE (patient_id, diagnosistype_id) " +
                ")");

        //Exercise Tables
        db.execSQL(" CREATE TABLE ExerciseType ( " +
                "    title text PRIMARY KEY, " +
                "    explanation text " +
                ")");
        db.execSQL("CREATE TABLE PatientExercise ( " +
                "    patient_id int NOT NULL, " +
                "    exercisetype_title text NOT NULL, " +
                "    FOREIGN KEY (patient_id) REFERENCES Patient(id) ON DELETE CASCADE, " +
                "    FOREIGN KEY (exercisetype_title) REFERENCES ExerciseType(title) ON DELETE CASCADE, " +
                "    UNIQUE (patient_id, exercisetype_title) " +
                ")");
        db.execSQL("CREATE TABLE ExercisePhoto ( " +
                "    id INTEGER PRIMARY KEY, " +
                "    patient_id int NOT NULL, " +
                "    photo BLOB NOT NULL, " +
                "    FOREIGN KEY (patient_id) REFERENCES Patient(id) ON DELETE CASCADE " +
                ")");

        //Note Table
        db.execSQL("CREATE TABLE Note ( " +
                "    id INTEGER PRIMARY KEY, " +
                "    patient_id int NOT NULL, " +
                "    note_bytes BLOB NOT NULL, " +
                "    selected boolean, " +
                "    FOREIGN KEY (patient_id) REFERENCES Patient(id) ON DELETE CASCADE " +
                ")");

        //Psychosocial Factors Tables
        db.execSQL("CREATE TABLE PsychoSocialBefore " +
                "( " +
                "    patient_id   int PRIMARY KEY, " +
                "    pain_xpos    int NOT NULL, " +
                "    pain_ypos    int NOT NULL, " +
                "    familiy_xpos int NOT NULL, " +
                "    familiy_ypos int NOT NULL, " +
                "    work_xpos    int NOT NULL, " +
                "    work_ypos    int NOT NULL, " +
                "    finance_xpos int NOT NULL, " +
                "    finance_ypos int NOT NULL, " +
                "    event_xpos   int NOT NULL, " +
                "    event_ypos   int NOT NULL, " +
                "    FOREIGN KEY (patient_id) REFERENCES Patient (id) ON DELETE CASCADE " +
                ")");
        db.execSQL("CREATE TABLE PsychoSocialAfter " +
                "( " +
                "    patient_id   int PRIMARY KEY, " +
                "    pain_xpos    int NOT NULL, " +
                "    pain_ypos    int NOT NULL, " +
                "    familiy_xpos int NOT NULL, " +
                "    familiy_ypos int NOT NULL, " +
                "    work_xpos    int NOT NULL, " +
                "    work_ypos    int NOT NULL, " +
                "    finance_xpos int NOT NULL, " +
                "    finance_ypos int NOT NULL, " +
                "    event_xpos   int NOT NULL, " +
                "    event_ypos   int NOT NULL, " +
                "    FOREIGN KEY (patient_id) REFERENCES Patient (id) ON DELETE CASCADE " +
                ")");
        db.execSQL("CREATE TABLE ImprovementReason " +
                "( " +
                "    patient_id   int PRIMARY KEY, " +
                "    drugs boolean, " +
                "    exercises boolean, " +
                "    awareness boolean, " +
                "    other_reason boolean, " +
                "    other_reason_text text, " +
                "    FOREIGN KEY (patient_id) REFERENCES Patient (id) ON DELETE CASCADE " +
                ")");

        //Media Tables
        db.execSQL("CREATE TABLE PatientImage " +
                "( " +
                "    patient_id int  NOT NULL, " +
                "    image_path text NOT NULL, " +
                "    FOREIGN KEY (patient_id) REFERENCES Patient (id) ON DELETE CASCADE, " +
                "    UNIQUE (patient_id, image_path) " +
                ")");
        db.execSQL("CREATE TABLE PatientVideo " +
                "( " +
                "    patient_id int  NOT NULL, " +
                "    video_path text NOT NULL, " +
                "    FOREIGN KEY (patient_id) REFERENCES Patient (id) ON DELETE CASCADE, " +
                "    UNIQUE (patient_id, video_path) " +
                ")");

        db.execSQL("CREATE TABLE PatientDocument " +
                "( " +
                "    patient_id int NOT NULL, " +
                "    document_path text NOT NULL, " +
                "    FOREIGN KEY (patient_id) REFERENCES Patient (id) ON DELETE CASCADE, " +
                "    UNIQUE (patient_id, document_path) " +
                ")");
        db.execSQL("CREATE TABLE WebsiteType " +
                "( " +
                "    id INTEGER PRIMARY KEY, " +
                "    name text NOT NULL, " +
                "    url text NOT NULL, " +
                "    description text " +
                ")");
        db.execSQL("CREATE TABLE PatientWebsite " +
                "( " +
                "    patient_id int  NOT NULL, " +
                "    website_id int NOT NULL, " +
                "    FOREIGN KEY (patient_id) REFERENCES Patient (id) ON DELETE CASCADE, " +
                "    FOREIGN KEY (website_id) REFERENCES WebsiteType (id) ON DELETE CASCADE, " +
                "    UNIQUE (patient_id, website_id) " +
                ")");

        //Pain Description Tables
        db.execSQL("CREATE TABLE PainBeginning " +
                "( " +
                "    patient_id int  PRIMARY KEY, " +
                "    intensity int, " +
                "    location_teeth BLOB, " +
                "    location_face_left BLOB, " +
                "    location_face_right BLOB, " +
                "    pain_quality text, " +
                "    pain_pattern text, " +
                "    dull boolean, " +
                "    pulling boolean, " +
                "    stinging boolean, " +
                "    pulsating boolean, " +
                "    burning boolean, " +
                "    pinsneedles boolean, " +
                "    tingling boolean, " +
                "    numb boolean, " +
                "    FOREIGN KEY (patient_id) REFERENCES Patient (id) ON DELETE CASCADE " +
                ")");
        db.execSQL("CREATE TABLE PainCurrent " +
                "( " +
                "    patient_id int  PRIMARY KEY, " +
                "    intensity int, " +
                "    location_teeth BLOB, " +
                "    location_face_left BLOB, " +
                "    location_face_right BLOB, " +
                "    pain_quality text, " +
                "    pain_pattern text, " +
                "    dull boolean, " +
                "    pulling boolean, " +
                "    stinging boolean, " +
                "    pulsating boolean, " +
                "    burning boolean, " +
                "    pinsneedles boolean, " +
                "    tingling boolean, " +
                "    numb boolean, " +
                "    FOREIGN KEY (patient_id) REFERENCES Patient (id) ON DELETE CASCADE " +
                ")");
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
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());

        // Inserting Row
        db.insert( "User", null, values);

        // Closing database connection
        db.close();
    }

    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query( "User", new String[] { "id",
                        "username", "password" }, "id = ?",
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
        String selectQuery = "SELECT * FROM User";

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
        String countQuery = "SELECT * FROM User";
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
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());

        // updating row
        return db.update( "User", values, "id = ?",
                new String[]{String.valueOf(user.getId())});
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete( "User", "id = ?",
                new String[] { String.valueOf(user.getId()) });
        db.close();
    }


    public void addPatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("shortname", patient.getShortname());
        values.put("gender", patient.getGender());

        // Inserting Row
        db.insert("Patient", null, values);

        // Closing database connection
        db.close();
    }

    public Patient getPatient(int patientId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("Patient", new String[] { "id",
                        "shortname", "gender" }, "id = ?",
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
        String selectQuery = "SELECT  * FROM Patient";

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
        values.put("shortname", patient.getShortname());
        values.put("gender", patient.getGender());

        // updating row
        return db.update("Patient", values, "id = ?",
                new String[]{String.valueOf(patientId)});
    }

    public void deletePatient(int patientId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Patient", "id = ?",
                new String[] { String.valueOf(patientId) });
        db.close();
    }

    public int selectLastPatientId(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Patient", new String[] {"id",
                "shortname", "gender" },null, null, null, null, null);
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
