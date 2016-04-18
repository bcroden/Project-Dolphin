package com.projectdolphin.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.projectdolphin.data.model.Assignment;
import com.projectdolphin.data.model.Category;
import com.projectdolphin.data.model.Class;

/**
 * Singleton for keeping the database alive throughout the application's life.
 * Based on example found at: http://www.androiddesignpatterns.com/2012/05/correctly-managing-your-sqlite-database.html
 */
public class DBAccessHelper {

    private SQLiteDatabase writableDB;
    private SQLiteDatabase readableDB;
    public static final String CLASS_DB_ID_INTENT_KEY = "CLASS_DB_ID_INTENT_KEY";
    public static final String CATEGORY_DB_ID_INTENT_KEY = "CATEGORY_DB_ID_INTENT_KEY";
    public static final String ASSIGNMENT_DB_ID_INTENT_KEY = "ASSIGNMENT_DB_ID_INTENT_KEY";

    public static DBAccessHelper getInstance(Context context) {
        if(instance == null)
            instance = new DBAccessHelper(context);
        return instance;
    }
    private DBAccessHelper(Context context) {
        openHelper = new DolphinSQLiteOpenHelper(context.getApplicationContext());
        writableDB = openHelper.getWritableDatabase();
        readableDB = openHelper.getReadableDatabase();
    }
    private static DBAccessHelper instance;

    /* Class methods */

    public List<Class> getAllClasses() {
        //TODO: Implement this using the openHelper
        return new LinkedList<>();
    }
    public Class getClassByID(long CLASS_DB_ID) {
        //TODO: Implement this using the openHelper
        return new Class(-1, 0, 1.0, 4.0, "title", new LinkedList<Integer>());
    }
    public void updateClass(Class _class) {
        //TODO: Implement this using the openHelper
    }
    public void insertClass(Class _class) {
        ContentValues values = new ContentValues();
        List<Integer> categoryIdList = new ArrayList<Integer>();
        categoryIdList = _class.getCategoryIDs();
        String categoryIdstring = listStringify(categoryIdList);
        values.put(DatabaseContract.DolphinColumns.COLUMN_TITLE, _class.getTitle());
        values.put(DatabaseContract.DolphinColumns.COLUMN_GRADE, _class.getGrade());
        values.put(DatabaseContract.DolphinColumns.COLUMN_WEIGHT, _class.getWeight());
        values.put(DatabaseContract.DolphinColumns.COLUMN_TIMESPENT, _class.getTimeSpentAsString());
        values.put(DatabaseContract.DolphinColumns.COLUMN_CATEGORY_IDS, categoryIdstring);
        writableDB.insert(DatabaseContract.DolphinColumns.CLASS_TABLE_NAME, null, values);
    }
    public void removeClassByID(long CLASS_DB_ID) {
        //TODO: Implement this using the openHelper
    }

    /* Category methods */
    public List<Category> getAllCategoriesForClassID(long CLASS_DB_ID) {
        //TODO: Implement this using the openHelper
        return new LinkedList<>();
    }
    public Category getCategoryByID(long CLASS_DB_ID) {
        //TODO: Implement this using the openHelper
        return new Category(-1, -1, 0, 1.0, 4.0, "title", new LinkedList<Integer>());
    }
    public void updateCategory(Category category) {
        //TODO: Implement this using the openHelper
    }
    public void insertCategory(Category category) {
        ContentValues values = new ContentValues();
        List<Integer> AssignmentIdList = new ArrayList<Integer>();
        AssignmentIdList = category.getAssignmentsIDs();
        String AssignmentIdstring = listStringify(AssignmentIdList);
        values.put(DatabaseContract.DolphinColumns.COLUMN_TITLE, category.getTitle());
        values.put(DatabaseContract.DolphinColumns.COLUMN_GRADE, category.getGrade());
        values.put(DatabaseContract.DolphinColumns.COLUMN_WEIGHT, category.getWeight());
        values.put(DatabaseContract.DolphinColumns.COLUMN_TIMESPENT, category.getTimeSpentAsString());
        values.put(DatabaseContract.DolphinColumns.COLUMN_PARENT_ID, category.getParent_DB_ID());
        values.put(DatabaseContract.DolphinColumns.COLUMN_ASSIGNMENT_IDS, AssignmentIdstring);
        writableDB.insert(DatabaseContract.DolphinColumns.CATEGORY_TABLE_NAME, null, values);
    }

    public void removeCategoryByID(long CATEGORY_DB_ID) {
        //TODO: Implement this using the openHelper
    }

    /* Assignment methods */
    public List<Assignment> getAllAssignmentsForCategoryID(long CATEGORY_DB_ID) {
        //TODO: Implement this using the openHelper
        return new LinkedList<>();
    }
    public Assignment getAssignmentByID(long ASSIGNMENT_DB_ID) {
        //TODO: Implement this using the openHelper
        return new Assignment(-1, -1, 0, 1.0, 4.0, "title");
    }
    public void updateAssignment(Assignment category) {
        //TODO: Implement this using the openHelper
    }
    public void insertAssignment(Assignment assignment) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DolphinColumns.COLUMN_TITLE, assignment.getTitle());
        values.put(DatabaseContract.DolphinColumns.COLUMN_GRADE, assignment.getGrade());
        values.put(DatabaseContract.DolphinColumns.COLUMN_WEIGHT, assignment.getWeight());
        values.put(DatabaseContract.DolphinColumns.COLUMN_TIMESPENT, assignment.getTimeSpentAsString());
        values.put(DatabaseContract.DolphinColumns.COLUMN_PARENT_ID, assignment.getParentDBID());
        writableDB.insert(DatabaseContract.DolphinColumns.ASSIGNMENT_TABLE_NAME, null, values);
    }
    public void removeAssignmentByID(long ASSIGNMENT_DB_ID) {
        //TODO: Implement this using the openHelper
    }

    public String listStringify(List<Integer> listToStringify){
        String stringifiedList = null;
        if(listToStringify != null) {
            for (int value : listToStringify) {
                if (stringifiedList == null) {
                    stringifiedList = Integer.toString(value);
                } else {
                    stringifiedList = stringifiedList + "," + Integer.toString(value);
                }
            }
        }
        return stringifiedList;
    }

    public List<Integer> listUnstringify(String stringList){
        ArrayList<Integer> UnstringifiedList = null;
        String[] parsedString = null;
        if(stringList != null){
            parsedString = stringList.split(",");
        }
        for(int i = 0; i < parsedString.length; i++){
            int convertedString = Integer.parseInt(parsedString[i]);
            UnstringifiedList.add(convertedString);
        }
        return UnstringifiedList;
    }

    private DolphinSQLiteOpenHelper openHelper;
}
