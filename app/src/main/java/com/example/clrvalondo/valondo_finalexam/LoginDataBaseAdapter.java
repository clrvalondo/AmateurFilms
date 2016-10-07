package com.example.clrvalondo.valondo_finalexam;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDataBaseAdapter {
    static final String DATABASE_NAME = "user.db";
    static final int DATABASE_VERSION = 1;
    public static final String NAME_COLUMN = "user";
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table " + "LOGIN" +
            "( " + "ID" + " integer primary key autoincrement," + "USERNAME  text UNIQUE,PASSWORD text, FIRSTNAME text, LASTNAME text, EMAIL text UNIQUE); ";
    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;

    public LoginDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public LoginDataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void insertEntry(String fname, String lname, String uname, String email, String password) {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.

        newValues.put("FIRSTNAME", fname);
        newValues.put("LASTNAME", lname);
        newValues.put("USERNAME", uname);
        newValues.put("EMAIL", email);
        newValues.put("PASSWORD", password);

        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public String getSinlgeEntry(String em) {
        Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{em}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {

            cursor = db.query("LOGIN", null, " EMAIL=?", new String[]{em}, null, null, null);
            if (cursor.getCount() < 1) // UserName Not Exist
            {
                cursor.close();
                return "NOT EXIST";
            }

            cursor.moveToFirst();
            String password2 = cursor.getString(cursor.getColumnIndex("PASSWORD"));
            cursor.close();
            return password2;

        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }

    public String getInfo(String em) {
        Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{em}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor = db.query("LOGIN", null, " EMAIL=?", new String[]{em}, null, null, null);
            if (cursor.getCount() < 1) // UserName Not Exist
            {
                cursor.close();
                return "NOT EXIST";
            }

            cursor.moveToFirst();
            String info = "First Name: " + cursor.getString(cursor.getColumnIndex("FIRSTNAME")) + "\nLast Name: " + cursor.getString(cursor.getColumnIndex("LASTNAME")) + "\nUSERNAME: " + cursor.getString(cursor.getColumnIndex("USERNAME")) + "\nEMAIL: " + cursor.getString(cursor.getColumnIndex("EMAIL")) + "\nPASSWORD: " + cursor.getString(cursor.getColumnIndex("PASSWORD"));
            cursor.close();
            return info;
        }
        cursor.moveToFirst();
        String info = "First Name: " + cursor.getString(cursor.getColumnIndex("FIRSTNAME")) + "\nLast Name: " + cursor.getString(cursor.getColumnIndex("LASTNAME")) + "\nUSERNAME: " + cursor.getString(cursor.getColumnIndex("USERNAME")) + "\nEMAIL: " + cursor.getString(cursor.getColumnIndex("EMAIL")) + "\nPASSWORD: " + cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return info;
    }

    public boolean ifExist(String em) {
        Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{em}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor = db.query("LOGIN", null, " EMAIL=?", new String[]{em}, null, null, null);
            if (cursor.getCount()< 1) // UserName Not Exist
            {
                cursor.close();
                return false;
                //not exist
            }
            cursor.moveToFirst();
            cursor.close();
            return true;

        }
        cursor.moveToFirst();
        cursor.close();
        return true;
    }
}