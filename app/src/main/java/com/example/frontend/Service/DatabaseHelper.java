package com.example.frontend.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.frontend.Models.User;

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

    public DatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
}
