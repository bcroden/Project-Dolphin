package com.projectdolphin.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.projectdolphin.data.model.Assignment;

public class AssignmentSQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    private static final String TAG = "ITEM";

    public AssignmentSQLiteOpenHelper(Context context) {
        super(context, DatabaseContract.DATABASE_NAME,
                null, DatabaseContract.DATABASE_VERSION);
    }

    // when no database exists in disk and the helper class needs to create a new one.
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    public void init(SQLiteDatabase db) {
        Log.d(TAG, DatabaseContract.DolphinColumns.DELETE_ASSIGNMENT_TABLE);
        db.execSQL(DatabaseContract.DolphinColumns.DELETE_ASSIGNMENT_TABLE);
        Log.d(TAG, DatabaseContract.DolphinColumns.CREATE_ASSIGNMENT_TABLE);
        db.execSQL(DatabaseContract.DolphinColumns.CREATE_ASSIGNMENT_TABLE);
        Log.d(TAG, "database created.");
    }

    // when there is a database version mismatch meaning that the version.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("TaskDBAdapter", "Upgrading from version " +
                oldVersion + " to " + newVersion + ". all data destroyed.");
        db.execSQL(DatabaseContract.DolphinColumns.DELETE_ASSIGNMENT_TABLE);
        Log.d(TAG, "database dropped.");
        db.execSQL(DatabaseContract.DolphinColumns.CREATE_ASSIGNMENT_TABLE);
    }


}
