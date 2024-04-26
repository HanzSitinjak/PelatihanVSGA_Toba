package com.example.finalproject_hansagung;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MeowDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String COLUMN_ID_USER = "id_user";
    private static final String COLUMN_USERNAME = "username";
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID_NOTES = "id_notes";
    public static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "konten";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableQuery = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createUserTableQuery);

        String createNotesTableQuery = "CREATE TABLE " + TABLE_NOTES + " (" +
                COLUMN_ID_NOTES + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_CONTENT + " TEXT)";
        db.execSQL(createNotesTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public boolean insertData(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, contentValues);
        db.close(); // Close the database connection
        return result != -1; // Return true if inserted successfully, false otherwise
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase(); // Use readable database
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close(); // Close the cursor
        db.close(); // Close the database connection
        return exists;
    }

    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase(); // Use readable database
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close(); // Close the cursor
        db.close(); // Close the database connection
        return exists;
    }

    public boolean addNote(String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_CONTENT, content);
        long result = db.insert(TABLE_NOTES, null, values);
        db.close();
        return result != -1; // Returns true if insertion is successful
    }

    public String[] getDiaryDetail(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTES + " WHERE " + COLUMN_ID_NOTES + " = ?", new String[]{String.valueOf(id)});

        String[] diaryDetail = new String[2];
        if (cursor.moveToFirst()) {
            diaryDetail[0] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            diaryDetail[1] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT));
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return diaryDetail;
    }

    public String[] getUserDetail(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_ID_USER + " = ?", new String[]{String.valueOf(id)});

        String[] userDetail = null;
        if (cursor != null && cursor.moveToFirst()) {
            userDetail = new String[3];
            userDetail[0] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
            userDetail[1] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
            userDetail[2] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
        }
        if(cursor != null) {
            cursor.close();
        }
        db.close();
        return userDetail;
    }

    public boolean updateDiary(long id, String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_CONTENT, content);
        int updatedRows = db.update(TABLE_NOTES, values, COLUMN_ID_NOTES + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return updatedRows > 0; // Returns true if update is successful
    }

    public boolean deleteDiary(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_NOTES, COLUMN_ID_NOTES + "=?", new String[]{String.valueOf(id)});
        db.close();
        return deletedRows > 0;
    }

    public Cursor getAllNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT id_notes AS _id, * FROM " + TABLE_NOTES, null);
    }

    public long getLoggedInUserID(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID_USER + " FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{email, password});

        long loggedInUserID = -1;
        if (cursor.moveToFirst()) {
            loggedInUserID = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID_USER));
        }
        cursor.close();
        db.close();
        return loggedInUserID;
    }
}
