package com.projectdolphin.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import com.projectdolphin.data.model.Class;

public class ClassSQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    private static final String TAG = "ITEM";

    public ClassSQLiteOpenHelper(Context context) {
        super(context, DatabaseContract.DATABASE_NAME,
                null, DatabaseContract.DATABASE_VERSION);
    }

    // when no database exists in disk and the helper class needs to create a new one.
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    public void init(SQLiteDatabase db) {
        Log.d(TAG, DatabaseContract.DolphinColumns.DELETE_CLASS_TABLE);
        db.execSQL(DatabaseContract.DolphinColumns.DELETE_CLASS_TABLE);
        Log.d(TAG, DatabaseContract.DolphinColumns.CREATE_CLASS_TABLE);
        db.execSQL(DatabaseContract.DolphinColumns.CREATE_CLASS_TABLE);
        Log.d(TAG, "database created.");
    }

    // when there is a database version mismatch meaning that the version.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("TaskDBAdapter", "Upgrading from version " +
                oldVersion + " to " + newVersion + ". all data destroyed.");
        db.execSQL(DatabaseContract.DolphinColumns.DELETE_CLASS_TABLE);
        Log.d(TAG, "database dropped.");
        db.execSQL(DatabaseContract.DolphinColumns.CREATE_CLASS_TABLE);
    }

    public void insert(SQLiteDatabase db,  Class localClass) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DolphinColumns.COLUMN_TITLE, localClass.getTitle());
        values.put(DatabaseContract.DolphinColumns.COLUMN_GRADE, localClass.getGrade());
        values.put(DatabaseContract.DolphinColumns.COLUMN_WEIGHT, localClass.getWeight());
        values.put(DatabaseContract.DolphinColumns.COLUMN_TIMESPENT, localClass.getTimeSpentAsString());
        db.insert(DatabaseContract.DolphinColumns.CLASS_TABLE_NAME, null, values);
    }
}
