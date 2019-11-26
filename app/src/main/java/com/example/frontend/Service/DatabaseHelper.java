package com.example.frontend.Service;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.frontend.Models.DiagnosisType;
import com.example.frontend.Models.DrugType;
import com.example.frontend.Models.ExercisePhoto;
import com.example.frontend.Models.ExerciseType;
import com.example.frontend.Models.Patient;
import com.example.frontend.Models.PatientDiagnosis;
import com.example.frontend.Models.PatientDrug;
import com.example.frontend.Models.PatientExercise;
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
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON");
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


    //User table related functions

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


    //Patient table related functions

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
        String selectQuery = "SELECT  * FROM Patient ORDER BY shortname";

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


    //DrugType table related functions

    public void addDrugType(DrugType drugType) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", drugType.getName());
        values.put("description", drugType.getDescription());

        // Inserting Row
        db.insert("DrugType", null, values);

        // Closing database connection
        db.close();
    }

    public DrugType getDrugType(int drugTypeId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("DrugType", new String[] { "id",
                        "name", "description" }, "id = ?",
                new String[] { String.valueOf(drugTypeId) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DrugType drugType = new DrugType(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return drugType
        return drugType;
    }

    public List<DrugType> getAllDrugTypes() {
        List<DrugType> drugTypeList = new ArrayList<DrugType>();
        // Select All Query
        String selectQuery = "SELECT * FROM DrugType ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DrugType drugType = new DrugType();
                drugType.setId(Integer.parseInt(cursor.getString(0)));
                drugType.setName(cursor.getString(1));
                drugType.setDescription(cursor.getString(2));
                // Adding drugType to list
                drugTypeList.add(drugType);
            } while (cursor.moveToNext());
        }

        // return drugType list
        return drugTypeList;
    }

    public int updateDrugType(int drugTypeId, DrugType drugType) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", drugType.getName());
        values.put("description", drugType.getDescription());

        // updating row
        return db.update("DrugType", values, "id = ?",
                new String[]{String.valueOf(drugTypeId)});
    }

    public void deleteDrugType(int drugTypeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("DrugType", "id = ?",
                new String[] { String.valueOf(drugTypeId) });
        db.close();
    }

    public int selectLastDrugTypeId(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("DrugType", new String[] {"id",
                "name", "description" },null, null, null, null, null);
        cursor.moveToLast();
        int lastId = Integer.parseInt(cursor.getString(0));
        return lastId;
    }


    //PatientDrug table related functions

    public void addPatientDrug(PatientDrug patientDrug) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("patient_id", patientDrug.getPatientId());
        values.put("drugtype_id", patientDrug.getDrugTypeId());
        values.put("amount", patientDrug.getAmount());
        values.put("dosis", patientDrug.getDosis());

        // Inserting Row
        db.insert("PatientDrug", null, values);

        // Closing database connection
        db.close();
    }

    public PatientDrug getPatientDrug(int patientId, int drugTypeId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("PatientDrug", new String[] { "patient_id", "drugtype_id",
                        "amount", "dosis" }, "patient_id = ? AND drugtype_id = ?",
                new String[] {String.valueOf(patientId), String.valueOf(drugTypeId)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        PatientDrug patientDrug = new PatientDrug(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),
                cursor.getString(2), cursor.getString(3));
        // return patientDrug
        return patientDrug;
    }

    public List<PatientDrug> getAllPatientDrugs() {
        List<PatientDrug> patientDrugList = new ArrayList<PatientDrug>();
        // Select All Query
        String selectQuery = "SELECT * FROM PatientDrug ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PatientDrug patientDrug = new PatientDrug();
                patientDrug.setPatientId(Integer.parseInt(cursor.getString(0)));
                patientDrug.setDrugId(Integer.parseInt(cursor.getString(1)));
                patientDrug.setAmount(cursor.getString(2));
                patientDrug.setDosis(cursor.getString(3));
                // Adding patientDrug to list
                patientDrugList.add(patientDrug);
            } while (cursor.moveToNext());
        }

        // return patientDrug list
        return patientDrugList;
    }

    public List<PatientDrug> getAllDrugsOfPatient(int patientId){
        List<PatientDrug> drugsOfPatientList = new ArrayList<PatientDrug>();
        // Select All Query
        String selectQuery = "SELECT * FROM PatientDrug WHERE patient_id = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(patientId)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PatientDrug patientDrug = new PatientDrug();
                patientDrug.setPatientId(patientId);
                patientDrug.setDrugId(Integer.parseInt(cursor.getString(1)));
                patientDrug.setAmount(cursor.getString(2));
                patientDrug.setDosis(cursor.getString(3));
                // Adding patientDrug to list
                drugsOfPatientList.add(patientDrug);
            } while (cursor.moveToNext());
        }

        // return patientDrug list
        return drugsOfPatientList;
    }

    public int updatePatientDrug(int patientId, int drugTypeId, PatientDrug patientDrug) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("patient_id", patientDrug.getPatientId());
        values.put("drugtype_id", patientDrug.getDrugTypeId());
        values.put("amount", patientDrug.getAmount());
        values.put("dosis", patientDrug.getDosis());

        // updating row
        return db.update("PatientDrug", values, "patient_id = ? AND drugtype_id= ?",
                new String[]{String.valueOf(patientId),String.valueOf(drugTypeId)});
    }

    public void deletePatientDrug(int patientId, int drugTypeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("PatientDrug", "patient_id = ? AND drugtype_id = ?",
                new String[] {String.valueOf(patientId),String.valueOf(drugTypeId)});
        db.close();
    }


    //DiagnosisType table related functions

    public void addDiagnosisType(DiagnosisType diagnosisType) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", diagnosisType.getName());
        values.put("type", diagnosisType.getType());
        values.put("description", diagnosisType.getDescription());

        // Inserting Row
        db.insert("DiagnosisType", null, values);

        // Closing database connection
        db.close();
    }

    public DiagnosisType getDiagnosisType(int diagnosisTypeId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("DiagnosisType", new String[] { "id",
                        "name","type", "description" }, "id = ?",
                new String[] { String.valueOf(diagnosisTypeId) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DiagnosisType diagnosisType = new DiagnosisType(
                Integer.parseInt(cursor.getString(0)), //id
                cursor.getString(1), //name
                cursor.getString(2), //type
                cursor.getString(3)); //description
        // return diagnosisType
        return diagnosisType;
    }

    public List<DiagnosisType> getAllDiagnosisTypes() {
        List<DiagnosisType> diagnosisTypeList = new ArrayList<DiagnosisType>();
        // Select All Query
        String selectQuery = "SELECT * FROM DiagnosisType ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DiagnosisType diagnosisType = new DiagnosisType();
                diagnosisType.setId(Integer.parseInt(cursor.getString(0)));
                diagnosisType.setName(cursor.getString(1));
                diagnosisType.setType(cursor.getString(2));
                diagnosisType.setDescription(cursor.getString(3));
                // Adding diagnosisType to list
                diagnosisTypeList.add(diagnosisType);
            } while (cursor.moveToNext());
        }

        // return diagnosisType list
        return diagnosisTypeList;
    }

    public List<DiagnosisType> getDiagnosisTypesByClass(String type){
        List<DiagnosisType> diagnosisTypesOfClass = new ArrayList<DiagnosisType>();
        // Select All Query
        String selectQuery = "SELECT * FROM DiagnosisType WHERE type = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{type});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DiagnosisType diagnosisType = new DiagnosisType();
                diagnosisType.setId(Integer.parseInt(cursor.getString(0)));
                diagnosisType.setName(cursor.getString(1));
                diagnosisType.setType(type);
                diagnosisType.setDescription(cursor.getString(3));
                // Adding patientDrug to list
                diagnosisTypesOfClass.add(diagnosisType);
            } while (cursor.moveToNext());
        }

        // return patientDrug list
        return diagnosisTypesOfClass;
    }

    public List<String> getAllDiagnosisTypeClasses(){
        List<String> allDiagnosisTypeClasses = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT DISTINCT type FROM DiagnosisType ORDER BY type";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding class to list
                allDiagnosisTypeClasses.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // return class list
        return allDiagnosisTypeClasses;
    }

    public int updateDiagnosisType(int diagnosisTypeId, DiagnosisType diagnosisType) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", diagnosisType.getName());
        values.put("type", diagnosisType.getType());
        values.put("description", diagnosisType.getDescription());

        // updating row
        return db.update("DiagnosisType", values, "id = ?",
                new String[]{String.valueOf(diagnosisTypeId)});
    }

    public void deleteDiagnosisType(int diagnosisTypeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("DiagnosisType", "id = ?",
                new String[] { String.valueOf(diagnosisTypeId) });
        db.close();
    }

    public int selectLastDiagnosisTypeId(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("DiagnosisType", new String[] {"id",
                "name", "type", "description" },null, null, null, null, null);
        cursor.moveToLast();
        int lastId = Integer.parseInt(cursor.getString(0));
        return lastId;
    }


    //PatientDiagnosis table related functions

    public void addPatientDiagnosis(PatientDiagnosis patientDiagnosis) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("patient_id", patientDiagnosis.getPatientId());
        values.put("diagnosistype_id", patientDiagnosis.getDiagnosisId());
        values.put("comment", patientDiagnosis.getComment());

        // Inserting Row
        db.insert("PatientDiagnosis", null, values);

        // Closing database connection
        db.close();
    }

    public PatientDiagnosis getPatientDiagnosis(int patientId, int diagnosisTypeId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("PatientDiagnosis", new String[] { "patient_id", "diagnosistype_id",
                        "comment" }, "patient_id = ? AND diagnosistype_id = ?",
                new String[] {String.valueOf(patientId), String.valueOf(diagnosisTypeId)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        PatientDiagnosis patientDiagnosis = new PatientDiagnosis(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),
                cursor.getString(2));
        // return patientDiagnosis
        return patientDiagnosis;
    }

    public List<PatientDiagnosis> getAllPatientDiagnoses() {
        List<PatientDiagnosis> patientDiagnosisList = new ArrayList<PatientDiagnosis>();
        // Select All Query
        String selectQuery = "SELECT * FROM PatientDiagnosis ORDER BY patient_id";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PatientDiagnosis patientDiagnosis = new PatientDiagnosis();
                patientDiagnosis.setPatientId(Integer.parseInt(cursor.getString(0)));
                patientDiagnosis.setDiagnosisId(Integer.parseInt(cursor.getString(1)));
                patientDiagnosis.setComment(cursor.getString(2));
                // Adding patientDiagnosis to list
                patientDiagnosisList.add(patientDiagnosis);
            } while (cursor.moveToNext());
        }

        // return patientDiagnosis list
        return patientDiagnosisList;
    }

    public List<PatientDiagnosis> getAllDiagnosesOfPatient(int patientId){
        List<PatientDiagnosis> diagnosesOfPatientList = new ArrayList<PatientDiagnosis>();
        // Select All Query
        String selectQuery = "SELECT * FROM PatientDiagnosis WHERE patient_id = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(patientId)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PatientDiagnosis patientDiagnosis = new PatientDiagnosis();
                patientDiagnosis.setPatientId(patientId);
                patientDiagnosis.setDiagnosisId(Integer.parseInt(cursor.getString(1)));
                patientDiagnosis.setComment(cursor.getString(2));
                // Adding patientDiagnosis to list
                diagnosesOfPatientList.add(patientDiagnosis);
            } while (cursor.moveToNext());
        }

        // return patientDiagnosis list
        return diagnosesOfPatientList;
    }

    public List<PatientDiagnosis> getPatientDiagnosisOfClass(int patientId, String type){
        List<PatientDiagnosis> selectedPatientDiagnoses = new ArrayList<PatientDiagnosis>();
        // Select All Query
        String selectQuery = "SELECT patient_id, diagnosistype_id, type, comment FROM PatientDiagnosis " +
                "INNER JOIN DiagnosisType " +
                "ON PatientDiagnosis.diagnosistype_id =  DiagnosisType.id " +
                "WHERE PatientDiagnosis.patient_id = ? AND DiagnosisType.type = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(patientId), type});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PatientDiagnosis patientDiagnosis = new PatientDiagnosis();
                patientDiagnosis.setPatientId(patientId);
                patientDiagnosis.setDiagnosisId(Integer.parseInt(cursor.getString(1)));
                patientDiagnosis.setComment(cursor.getString(2));

                // Adding patientDiagnosis to list
                selectedPatientDiagnoses.add(patientDiagnosis);
            } while (cursor.moveToNext());
        }

        // return patientDiagnosisList
        return selectedPatientDiagnoses;
    }

    public int updatePatientDiagnosis(int patientId, int diagnosisTypeId, PatientDiagnosis patientDiagnosis) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("patient_id", patientId);
        values.put("diagnosistype_id", diagnosisTypeId);
        values.put("comment", patientDiagnosis.getComment());

        // updating row
        return db.update("PatientDiagnosis", values, "patient_id = ? AND diagnosistype_id= ?",
                new String[]{String.valueOf(patientId),String.valueOf(diagnosisTypeId)});
    }

    public void deletePatientDiagnosis(int patientId, int diagnosisTypeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("PatientDiagnosis", "patient_id = ? AND diagnosistype_id = ?",
                new String[] {String.valueOf(patientId),String.valueOf(diagnosisTypeId)});
        db.close();
    }


    //ExerciseType table related functions

    public void addExerciseType(ExerciseType exerciseType){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", exerciseType.getTitle());
        values.put("explanation", exerciseType.getExplanation());

        // Inserting Row
        db.insert("ExerciseType", null, values);

        // Closing database connection
        db.close();
    }

    public List<ExerciseType> getAllExerciseTypes(){
        List<ExerciseType> exerciseTypeList = new ArrayList<ExerciseType>();
        // Select All Query
        String selectQuery = "SELECT * FROM ExerciseType ORDER BY title";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ExerciseType exerciseType = new ExerciseType();
                exerciseType.setTitle(cursor.getString(0));
                exerciseType.setExplanation(cursor.getString(1));
                // Adding exerciseType to list
                exerciseTypeList.add(exerciseType);
            } while (cursor.moveToNext());
        }

        // return exerciseType list
        return exerciseTypeList;
    }

    public ExerciseType getExerciseType(String title) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("ExerciseType", new String[] { "title",
                        "explanation"}, "title = ?",
                new String[] { title }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ExerciseType exerciseType = new ExerciseType(
                title, //title
                cursor.getString(1)); //explanation
        // return exerciseType
        return exerciseType;
    }

    public List<String> getAllExerciseTypeTitles(){
        List<String> allExerciseTypeTitles = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT DISTINCT title FROM ExerciseType ORDER BY title";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding title to list
                allExerciseTypeTitles.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // return title list
        return allExerciseTypeTitles;
    }

    public int updateExerciseType(String title, ExerciseType exerciseType) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("explanation", exerciseType.getExplanation());

        // updating row
        return db.update("ExerciseType", values, "title = ?",
                new String[]{String.valueOf(title)});
    }

    public void deleteExerciseType(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ExerciseType", "title = ?",
                new String[] { String.valueOf(title) });
        db.close();
    }


    //PatientExercise table related functions

    public void addPatientExercise(PatientExercise patientExercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("patient_id", patientExercise.getPatientId());
        values.put("exercisetype_title", patientExercise.getExerciseTypeTitle());

        // Inserting Row
        db.insert("PatientExercise", null, values);

        // Closing database connection
        db.close();
    }

    public List<PatientExercise> getAllPatientExercises() {
        List<PatientExercise> patientExerciseList = new ArrayList<PatientExercise>();
        // Select All Query
        String selectQuery = "SELECT * FROM PatientExercise ORDER BY patient_id";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PatientExercise patientExercise = new PatientExercise();
                patientExercise.setPatientId(Integer.parseInt(cursor.getString(0)));
                patientExercise.setExerciseTypeTitle(cursor.getString(1));
                // Adding patientExercise to list
                patientExerciseList.add(patientExercise);
            } while (cursor.moveToNext());
        }

        // return patientExercise list
        return patientExerciseList;
    }

    public List<PatientExercise> getAllExercisesOfPatient(int patientId){
        List<PatientExercise> exercisesOfPatientList = new ArrayList<PatientExercise>();
        // Select All Query
        String selectQuery = "SELECT * FROM PatientExercise WHERE patient_id = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(patientId)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PatientExercise patientExercise = new PatientExercise();
                patientExercise.setPatientId(patientId);
                patientExercise.setExerciseTypeTitle(cursor.getString(1));
                // Adding patientExercise to list
                exercisesOfPatientList.add(patientExercise);
            } while (cursor.moveToNext());
        }

        // return patientExercise list
        return exercisesOfPatientList;
    }

    public void deletePatientExercise(int patientId, String exercisetypeTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("PatientExercise", "patient_id = ? AND exercisetype_title = ?",
                new String[] {String.valueOf(patientId),exercisetypeTitle});
        db.close();
    }


    //ExercisePhoto table related functions

    public void addExercisePhoto(ExercisePhoto patientExercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("patient_id", patientExercise.getPatientId());
        values.put("photo", patientExercise.getPhotoBytes());

        // Inserting Row
        db.insert("ExercisePhoto", null, values);

        // Closing database connection
        db.close();
    }

    public List<ExercisePhoto> getAllExercisePhotos() {
        List<ExercisePhoto> patientExerciseList = new ArrayList<ExercisePhoto>();
        // Select All Query
        String selectQuery = "SELECT * FROM ExercisePhoto ORDER BY patient_id";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ExercisePhoto patientExercise = new ExercisePhoto();
                patientExercise.setId(Integer.parseInt(cursor.getString(0)));
                patientExercise.setPatientId(Integer.parseInt(cursor.getString(1)));
                patientExercise.setPhotoBytes(cursor.getBlob(2));
                // Adding patientExercise to list
                patientExerciseList.add(patientExercise);
            } while (cursor.moveToNext());
        }

        // return patientExercise list
        return patientExerciseList;
    }

    public List<ExercisePhoto> getAllExercisePhotosOfPatient(int patientId){
        List<ExercisePhoto> exercisesOfPatientList = new ArrayList<ExercisePhoto>();
        // Select All Query
        String selectQuery = "SELECT * FROM ExercisePhoto WHERE patient_id = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(patientId)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ExercisePhoto patientExercise = new ExercisePhoto();
                patientExercise.setId(Integer.parseInt(cursor.getString(0)));
                patientExercise.setPatientId(patientId);
                patientExercise.setPhotoBytes(cursor.getBlob(2));
                // Adding patientExercise to list
                exercisesOfPatientList.add(patientExercise);
            } while (cursor.moveToNext());
        }

        // return patientExercise list
        return exercisesOfPatientList;
    }

    public void deleteExercisePhoto(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ExercisePhoto", "id = ?",
                new String[] {String.valueOf(id)});
        db.close();
    }

    public int selectLastPhotoId(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("ExercisePhoto", new String[] {"id",
                "patient_id", "photo"},null, null, null, null, null);
        cursor.moveToLast();
        int lastId = Integer.parseInt(cursor.getString(0));
        return lastId;
    }
}
