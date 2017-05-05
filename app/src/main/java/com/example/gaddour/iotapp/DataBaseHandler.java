package com.example.gaddour.iotapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "IOT";

    // Contacts table name
    private static final String Table_VALEUR = "Valeurs";

    // Contacts Table Columns names
    private static final String COL1 = "bat";
    private static final String COL2 = "date";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_VALEUR = "CREATE TABLE " + Table_VALEUR + "("
                + COL1 + " TEXT," + COL2 + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE_VALEUR);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Table_VALEUR);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addValues(int bat , String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL1, String.valueOf(bat)); // Contact Name
        values.put(COL2, date); // Contact Phone

        // Inserting Row
        db.insert(Table_VALEUR, null, values);


        db.close(); // Closing database connection

    }
    // Getting single contact


    // Getting All Contacts
    public ArrayList< AccessValeur> getAllValeur() {
        ArrayList<AccessValeur> valeurList = new ArrayList<AccessValeur>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Table_VALEUR;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AccessValeur valeur = new AccessValeur();
                valeur.setBat(cursor.getString(0));
                valeur.setDate(cursor.getString(1));
                // Adding contact to list
                valeurList.add(valeur);
            } while (cursor.moveToNext());
        }

        // return contact list
        return valeurList;
        // Updating single contact
    }
}
