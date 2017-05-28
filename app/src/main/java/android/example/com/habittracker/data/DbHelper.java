/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.example.com.habittracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



/**
 * There exists a subclass of SQLiteOpenHelper that overrides onCreate() and onUpgrade().
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = DbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "habits.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;
    private SQLiteDatabase dbReadable;

    /**
     * Constructs a new instance of {@link DbHelper}.
     *
     * @param context of the app
     */
    public DbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        dbReadable = getReadableDatabase();
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_HABITS_TABLE = "CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME + " ("
                + HabitContract.HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitContract.HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, "
                + HabitContract.HabitEntry.COLUMN_HABIT_TIMESTAMP + " INTEGER NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_HABITS_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.v(LOG_TAG, "Database opened");
    }

    public void dropTable(SQLiteDatabase db) {
        String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + HabitContract.HabitEntry.TABLE_NAME;
        db.execSQL(SQL_DROP_TABLE);
    }

    public void deleteDatabase() {
        context.deleteDatabase(DATABASE_NAME);
    }

    /**
     * There is a single insert method that adds at least two values of two different datatypes
     * (e.g. INTEGER, STRING) into the database using a ContentValues object and the insert() method.
     * @param values
     * @return
     */
    public long insertData(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        // Insert the new row, returning the primary key value of the new row
        return db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
    }

    /**
     * this is a single read method
     * @return a cursor of results
     */
    public Cursor fetchAll() {

        SQLiteDatabase db = getDbReadable();

        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMN_HABIT_NAME,
                HabitContract.HabitEntry.COLUMN_HABIT_TIMESTAMP,

        };

        Cursor resultSet = db.query(
                HabitContract.HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        return resultSet;
    }


    public long now() {
        return System.currentTimeMillis();
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(db);
        onCreate(db);
    }

    public void closeDb() {
        if (dbReadable != null)
            dbReadable.close();
    }

    public SQLiteDatabase getDbReadable() {
        return dbReadable;
    }
}