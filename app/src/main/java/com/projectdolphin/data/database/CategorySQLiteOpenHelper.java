package com.projectdolphin.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.projectdolphin.data.model.Category;

public class CategorySQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    private static final String TAG = "ITEM";

    public CategorySQLiteOpenHelper(Context context) {
        super(context, DatabaseContract.DATABASE_NAME,
                null, DatabaseContract.DATABASE_VERSION);
    }

    // when no database exists in disk and the helper class needs to create a new one.
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    public void init(SQLiteDatabase db) {
        Log.d(TAG, DatabaseContract.CategoryColumns.DELETE_TABLE);
        db.execSQL(DatabaseContract.CategoryColumns.DELETE_TABLE);
        Log.d(TAG, DatabaseContract.CategoryColumns.CREATE_TABLE);
        db.execSQL(DatabaseContract.CategoryColumns.CREATE_TABLE);
        Log.d(TAG, "database created.");
    }

    // when there is a database version mismatch meaning that the version.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("TaskDBAdapter", "Upgrading from version " +
                oldVersion + " to " + newVersion + ". all data destroyed.");
        db.execSQL(DatabaseContract.CategoryColumns.DELETE_TABLE);
        Log.d(TAG, "database dropped.");
        db.execSQL(DatabaseContract.CategoryColumns.CREATE_TABLE);
    }

    public void delete(SQLiteDatabase db){
        db.execSQL(DatabaseContract.CategoryColumns.DELETE_TABLE);
        db.execSQL(DatabaseContract.CategoryColumns.CREATE_TABLE);
    }

    public void insert(SQLiteDatabase db,  Category category) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.CategoryColumns.COLUMN_TITLE, category.getTitle());
        values.put(DatabaseContract.CategoryColumns.COLUMN_GRADE, category.getGrade());
        values.put(DatabaseContract.CategoryColumns.COLUMN_WEIGHT, category.getWeight());
        values.put(DatabaseContract.CategoryColumns.COLUMN_TIMESPENT, category.getTimeSpentAsString());
        db.insert(DatabaseContract.CategoryColumns.TABLE_NAME, null, values);
    }
}
