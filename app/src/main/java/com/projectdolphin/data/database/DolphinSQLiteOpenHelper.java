package com.projectdolphin.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import com.projectdolphin.data.model.Class;

public class DolphinSQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    private static final String TAG = "ITEM";

    public DolphinSQLiteOpenHelper(Context context) {
        super(context, DatabaseContract.DATABASE_NAME,
                null, DatabaseContract.DATABASE_VERSION);
    }

    // when no database exists in disk and the helper class needs to create a new one.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.DolphinColumns.CREATE_CLASS_TABLE);
        db.execSQL(DatabaseContract.DolphinColumns.CREATE_CATEGORY_TABLE);
        db.execSQL(DatabaseContract.DolphinColumns.CREATE_ASSIGNMENT_TABLE);
    }


    // when there is a database version mismatch meaning that the version.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
           // copyDatabase();
        }
        Log.w("TaskDBAdapter", "Upgrading from version " +
                oldVersion + " to " + newVersion + ". all data destroyed.");
        db.execSQL(DatabaseContract.DolphinColumns.DELETE_CLASS_TABLE);
        db.execSQL(DatabaseContract.DolphinColumns.DELETE_CATEGORY_TABLE);
        db.execSQL(DatabaseContract.DolphinColumns.DELETE_ASSIGNMENT_TABLE);
        Log.d(TAG, "database dropped.");
        db.execSQL(DatabaseContract.DolphinColumns.CREATE_CLASS_TABLE);
        db.execSQL(DatabaseContract.DolphinColumns.CREATE_CATEGORY_TABLE);
        db.execSQL(DatabaseContract.DolphinColumns.CREATE_ASSIGNMENT_TABLE);
    }

}
