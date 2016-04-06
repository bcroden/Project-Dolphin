package com.projectdolphin.data.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.projectdolphin.data.model.Category;

public class CategorySQLiteOpenHelper extends SQLiteOpenHelper{

    private static final String TAG = "ITEM";

    public CategorySQLiteOpenHelper(Context context) {
        super(context, CategoryDatabaseContract.DATABASE_NAME,
                null, CategoryDatabaseContract.DATABASE_VERSION);
    }

    // when no database exists in disk and the helper class needs to create a new one.
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    public void init(SQLiteDatabase db) {
        Log.d(TAG, CategoryDatabaseContract.ItemColumns.DELETE_TABLE);
        db.execSQL(CategoryDatabaseContract.ItemColumns.DELETE_TABLE);
        Log.d(TAG, CategoryDatabaseContract.ItemColumns.CREATE_TABLE);
        db.execSQL(CategoryDatabaseContract.ItemColumns.CREATE_TABLE);
        Log.d(TAG, "database created.");
    }

    // when there is a database version mismatch meaning that the version.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("TaskDBAdapter", "Upgrading from version " +
                oldVersion + " to " + newVersion + ". all data destroyed.");
        db.execSQL(CategoryDatabaseContract.ItemColumns.DELETE_TABLE);
        Log.d(TAG, "database dropped.");
        db.execSQL(CategoryDatabaseContract.ItemColumns.CREATE_TABLE);
    }

    public void delete(SQLiteDatabase db){
        db.execSQL(CategoryDatabaseContract.ItemColumns.DELETE_TABLE);
        db.execSQL(CategoryDatabaseContract.ItemColumns.CREATE_TABLE);
    }

    public void insert(SQLiteDatabase db,  Category category) {
        ContentValues values = new ContentValues();
        values.put(CategoryDatabaseContract.ItemColumns.COLUMN_TITLE, category.getTitle());
        values.put(CategoryDatabaseContract.ItemColumns.COLUMN_GRADE, category.getGrade());
        values.put(CategoryDatabaseContract.ItemColumns.COLUMN_WEIGHT, category.getWeight());
        values.put(CategoryDatabaseContract.ItemColumns.COLUMN_TIMESPENT, category.getTimeSpentAsString());
        db.insert(CategoryDatabaseContract.ItemColumns.TABLE_NAME, null, values);
    }
}
