package com.projectdolphin.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.projectdolphin.data.model.Assignment;
import com.projectdolphin.data.model.Category;
import com.projectdolphin.data.model.Class;
import com.projectdolphin.data.model.ClassData;

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
        if (instance == null)
            instance = new DBAccessHelper(context);
        return instance;
    }
    private DBAccessHelper(Context context) {
        openHelper = new DolphinSQLiteOpenHelper(context.getApplicationContext());
        writableDB = openHelper.getWritableDatabase();
        readableDB = openHelper.getReadableDatabase();

        classColumns = new String[]{
                DatabaseContract.DolphinColumns._ID,
                DatabaseContract.DolphinColumns.COLUMN_TITLE,
                DatabaseContract.DolphinColumns.COLUMN_WEIGHT,
                DatabaseContract.DolphinColumns.COLUMN_GRADE,
                DatabaseContract.DolphinColumns.COLUMN_TIMESPENT,
                DatabaseContract.DolphinColumns.COLUMN_CATEGORY_IDS,
                DatabaseContract.DolphinColumns.COLUMN_GRADE_VALIDITY
        };
        categoryColumns = new String[]{
                DatabaseContract.DolphinColumns._ID,
                DatabaseContract.DolphinColumns.COLUMN_TITLE,
                DatabaseContract.DolphinColumns.COLUMN_WEIGHT,
                DatabaseContract.DolphinColumns.COLUMN_GRADE,
                DatabaseContract.DolphinColumns.COLUMN_TIMESPENT,
                DatabaseContract.DolphinColumns.COLUMN_PARENT_ID,
                DatabaseContract.DolphinColumns.COLUMN_ASSIGNMENT_IDS,
                DatabaseContract.DolphinColumns.COLUMN_GRADE_VALIDITY
        };
        assignmentColumns = new String[]{
                DatabaseContract.DolphinColumns._ID,
                DatabaseContract.DolphinColumns.COLUMN_TITLE,
                DatabaseContract.DolphinColumns.COLUMN_WEIGHT,
                DatabaseContract.DolphinColumns.COLUMN_GRADE,
                DatabaseContract.DolphinColumns.COLUMN_TIMESPENT,
                DatabaseContract.DolphinColumns.COLUMN_PARENT_ID,
                DatabaseContract.DolphinColumns.COLUMN_GRADE_VALIDITY
        };
    }
    private static DBAccessHelper instance;

    /* Class methods */

    public List<Class> getAllClasses() {
        //form query
        String sortOrder = DatabaseContract.DolphinColumns._ID + " DESC";

        //execute query
        Cursor cursor = readableDB.query(DatabaseContract.DolphinColumns.CLASS_TABLE_NAME,
                classColumns,
                null,
                null,
                null,
                null,
                sortOrder);

        List<Class> classes = new LinkedList<>();

        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
                classes.add(getClassFromCursor(cursor));
            cursor.close();
        }

        return classes;
    }
    public Class getClassByID(long CLASS_DB_ID) {
        //execute query
        Cursor cursor = getCursorFromAt(
                DatabaseContract.DolphinColumns.CLASS_TABLE_NAME,
                classColumns,
                CLASS_DB_ID
        );

        //process results
        Class _class = null;
        if (cursor != null) {
            cursor.moveToFirst();
            _class = getClassFromCursor(cursor);
            cursor.close();
        }
        return _class;
    }
    public void putClass(Class _class) {
        if (_class.needsToBeInserted())
            insertClass(_class);
        else
            updateClass(_class);
    }
    public void updateClass(Class _class) {
        //form query
        ContentValues contentValues = getAllClassContentValues(_class);

        String whereClause = DatabaseContract.DolphinColumns._ID + " = ?";
        String[] whereArgs = new String[]{Long.toString(_class.getDB_ID())};

        //execute query
        writableDB.update(
                DatabaseContract.DolphinColumns.CLASS_TABLE_NAME,
                contentValues,
                whereClause,
                whereArgs
        );
    }
    public void insertClass(Class _class) {
        ContentValues values = getAllClassContentValues(_class);
        long db_id = writableDB.insert(DatabaseContract.DolphinColumns.CLASS_TABLE_NAME, null, values);
        _class.setDB_ID(db_id);
    }
    public void removeClassByID(long CLASS_DB_ID) {
        //prep db
        prepDBToRemoveClass(CLASS_DB_ID);

        //form query
        String whereClause = DatabaseContract.DolphinColumns._ID + " = ?";
        String[] whereArgs = new String[]{Long.toString(CLASS_DB_ID)};

        //execute query
        writableDB.delete(
                DatabaseContract.DolphinColumns.CLASS_TABLE_NAME,
                whereClause,
                whereArgs
        );
    }

    //get all of the data which belongs to a particular class
    public ClassData getAllDataFor(long CLASS_DB_ID) {
        Class _class = getClassByID(CLASS_DB_ID);

        Map<Category, List<Assignment>> categoryListMap = new HashMap<>();
        for(long cat_db_id : _class.getCategoryIDs()) {
            Category category = getCategoryByID(cat_db_id);
            List<Assignment> assignments = getAllAssignmentsForCategoryID(cat_db_id);
            categoryListMap.put(category, assignments);
        }
        return new ClassData(_class, categoryListMap);
    }

    /* Category methods */
    public List<Category> getAllCategoriesForClassID(long CLASS_DB_ID) {
        Cursor cursor = getCursorFromWithParent(
                DatabaseContract.DolphinColumns.CATEGORY_TABLE_NAME,
                categoryColumns,
                CLASS_DB_ID
        );

        //compile list
        List<Category> categories = new LinkedList<>();

        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
                categories.add(getCategoryFromCursor(cursor));
            cursor.close();
        }

        return categories;
    }
    public Category getCategoryByID(long CATEGORY_DB_ID) {
        //execute query
        Cursor cursor = getCursorFromAt(
                DatabaseContract.DolphinColumns.CATEGORY_TABLE_NAME,
                categoryColumns,
                CATEGORY_DB_ID
        );

        //process results
        Category category = null;
        if (cursor != null) {
            cursor.moveToFirst();
            category = getCategoryFromCursor(cursor);
            cursor.close();
        }

        return category;
    }
    public void putCategory(Category category) {
        if (category.needsToBeInserted())
            insertCategory(category);
        else
            updateCategory(category);

        rippleDBAfterUpdating(category);
    }
    private void updateCategory(Category category) {
        //form query
        ContentValues contentValues = getAllCategoryContentValues(category);

        String whereClause = DatabaseContract.DolphinColumns._ID + " = ?";
        String[] whereArgs = new String[]{Long.toString(category.getDB_ID())};

        //execute query
        writableDB.update(
                DatabaseContract.DolphinColumns.CATEGORY_TABLE_NAME,
                contentValues,
                whereClause,
                whereArgs
        );
    }
    private void insertCategory(Category category) {
        ContentValues values = getAllCategoryContentValues(category);
        long db_id = writableDB.insert(DatabaseContract.DolphinColumns.CATEGORY_TABLE_NAME, null, values);
        category.setDB_ID(db_id);
    }
    public void removeCategoryByID(long CATEGORY_DB_ID) {
        //prep db
        prepDBToRemoveCategory(CATEGORY_DB_ID);

        //form query
        String whereClause = DatabaseContract.DolphinColumns._ID + " = ?";
        String[] whereArgs = new String[]{Long.toString(CATEGORY_DB_ID)};

        //execute query
        writableDB.delete(
                DatabaseContract.DolphinColumns.CATEGORY_TABLE_NAME,
                whereClause,
                whereArgs
        );
    }

    /* Assignment methods */
    public List<Assignment> getAllAssignmentsForCategoryID(long CATEGORY_DB_ID) {
        //query database
        Cursor cursor = getCursorFromWithParent(
                DatabaseContract.DolphinColumns.ASSIGNMENT_TABLE_NAME,
                assignmentColumns,
                CATEGORY_DB_ID
        );

        //collect results
        List<Assignment> assignments = new LinkedList<>();
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
                assignments.add(getAssignmentFromCursor(cursor));
            cursor.close();
        }
        return assignments;
    }
    public Assignment getAssignmentByID(long ASSIGNMENT_DB_ID) {
        //get cursor
        Cursor cursor = getCursorFromAt(
                DatabaseContract.DolphinColumns.ASSIGNMENT_TABLE_NAME,
                assignmentColumns,
                ASSIGNMENT_DB_ID
        );

        //collect result
        Assignment assignment = null;
        if (cursor != null) {
            cursor.moveToFirst();
            assignment = getAssignmentFromCursor(cursor);
            cursor.close();
        }

        return assignment;
    }
    public void putAssignment(Assignment assignment) {
        if (assignment.needsToBeInserted())
            insertAssignment(assignment);
        else
            updateAssignment(assignment);

        rippleDBAfterUpdating(assignment);
    }
    private void updateAssignment(Assignment assignment) {
        //form query
        ContentValues contentValues = getAllAssignmentContentValues(assignment);

        String whereClause = DatabaseContract.DolphinColumns._ID + " = ?";
        String[] whereArgs = new String[]{Long.toString(assignment.getDB_ID())};

        //execute query
        writableDB.update(
                DatabaseContract.DolphinColumns.ASSIGNMENT_TABLE_NAME,
                contentValues,
                whereClause,
                whereArgs
        );
    }
    private void insertAssignment(Assignment assignment) {
        ContentValues values = getAllAssignmentContentValues(assignment);
        long db_id = writableDB.insert(DatabaseContract.DolphinColumns.ASSIGNMENT_TABLE_NAME, null, values);
        assignment.setDB_ID(db_id);
    }
    public void removeAssignmentByID(long ASSIGNMENT_DB_ID) {
        Assignment removedAssignment = getAssignmentByID(ASSIGNMENT_DB_ID);

        //form query
        String whereClause = DatabaseContract.DolphinColumns._ID + " = ?";
        String[] whereArgs = new String[]{Long.toString(ASSIGNMENT_DB_ID)};

        //execute query
        writableDB.delete(
                DatabaseContract.DolphinColumns.ASSIGNMENT_TABLE_NAME,
                whereClause,
                whereArgs
        );

        rippleDBAfterUpdating(removedAssignment);
    }

    //Helper methods to clean up insert and update methods
    private ContentValues getAllClassContentValues(Class _class) {
        ContentValues values = new ContentValues();
        List<Long> categoryIdList = _class.getCategoryIDs();
        String categoryIdstring = listStringify(categoryIdList);
        values.put(DatabaseContract.DolphinColumns.COLUMN_TITLE, _class.getTitle());
        values.put(DatabaseContract.DolphinColumns.COLUMN_GRADE, _class.getGrade());
        values.put(DatabaseContract.DolphinColumns.COLUMN_WEIGHT, _class.getWeight());
        values.put(DatabaseContract.DolphinColumns.COLUMN_TIMESPENT, _class.getTimeSpentMillis());
        values.put(DatabaseContract.DolphinColumns.COLUMN_CATEGORY_IDS, categoryIdstring);
        values.put(DatabaseContract.DolphinColumns.COLUMN_GRADE_VALIDITY, _class.isGradeValid());
        return values;
    }
    private ContentValues getAllCategoryContentValues(Category category) {
        ContentValues values = new ContentValues();
        List<Long> AssignmentIdList = category.getAssignmentsIDs();
        String AssignmentIdstring = listStringify(AssignmentIdList);
        values.put(DatabaseContract.DolphinColumns.COLUMN_TITLE, category.getTitle());
        values.put(DatabaseContract.DolphinColumns.COLUMN_GRADE, category.getGrade());
        values.put(DatabaseContract.DolphinColumns.COLUMN_WEIGHT, category.getWeight());
        values.put(DatabaseContract.DolphinColumns.COLUMN_TIMESPENT, category.getTimeSpentMillis());
        values.put(DatabaseContract.DolphinColumns.COLUMN_PARENT_ID, category.getParentDB_ID());
        values.put(DatabaseContract.DolphinColumns.COLUMN_ASSIGNMENT_IDS, AssignmentIdstring);
        values.put(DatabaseContract.DolphinColumns.COLUMN_GRADE_VALIDITY, category.isGradeValid());
        return values;
    }
    private ContentValues getAllAssignmentContentValues(Assignment assignment) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DolphinColumns.COLUMN_TITLE, assignment.getTitle());
        values.put(DatabaseContract.DolphinColumns.COLUMN_GRADE, assignment.getGrade());
        values.put(DatabaseContract.DolphinColumns.COLUMN_WEIGHT, assignment.getWeight());
        values.put(DatabaseContract.DolphinColumns.COLUMN_TIMESPENT, assignment.getTimeSpentMillis());
        values.put(DatabaseContract.DolphinColumns.COLUMN_PARENT_ID, assignment.getParentDB_ID());
        values.put(DatabaseContract.DolphinColumns.COLUMN_GRADE_VALIDITY, assignment.isGradeValid() ? 1 : 0);
        return values;
    }

    //Helper method which returns a cursor FROM a table with the given columns
    // AT a given database ID
    private Cursor getCursorFromAt(String tableName, String[] columns, final long DB_ID) {
        String selection = DatabaseContract.DolphinColumns._ID + " = ?";
        String[] selectionArgs = new String[]{Long.toString(DB_ID)};

        return readableDB.query(
                tableName,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }
    //Helper method which returns a cursor FROM a table with the given columns
    // WITH the given parent database ID
    private Cursor getCursorFromWithParent(String tableName, String[] columns, final long PARENT_DB_ID) {
        String selection = DatabaseContract.DolphinColumns.COLUMN_PARENT_ID + " = ?";
        String[] selectionArgs = new String[]{Long.toString(PARENT_DB_ID)};

        return readableDB.query(
                tableName,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    //Helper methods for doing repetitive and cumbersome things with cursors
    private Class getClassFromCursor(Cursor cursor) {
        long db_id = cursor.getLong(cursor.getColumnIndex(DatabaseContract.DolphinColumns._ID));
        String title = cursor.getString(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_TITLE));
        double weight = cursor.getDouble(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_WEIGHT));
        double grade = cursor.getDouble(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_GRADE));
        long timeSpent = cursor.getLong(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_TIMESPENT));
        String stringyCategoryIDs = cursor.getString(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_CATEGORY_IDS));
        boolean isGradeValid = cursor.getInt(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_GRADE_VALIDITY)) > 0;
        return new Class(db_id, timeSpent, grade, weight, title, listUnstringify(stringyCategoryIDs), isGradeValid);
    }
    private Category getCategoryFromCursor(Cursor cursor) {
        long db_id = cursor.getLong(cursor.getColumnIndex(DatabaseContract.DolphinColumns._ID));
        String title = cursor.getString(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_TITLE));
        double weight = cursor.getDouble(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_WEIGHT));
        double grade = cursor.getDouble(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_GRADE));
        long timeSpent = cursor.getLong(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_TIMESPENT));
        long parentDB_ID = cursor.getLong(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_PARENT_ID));
        String stringyAssignmentIDs = cursor.getString(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_ASSIGNMENT_IDS));
        boolean isGradeValid = cursor.getInt(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_GRADE_VALIDITY)) > 0;
        return new Category(db_id, parentDB_ID, timeSpent, grade, weight, title, listUnstringify(stringyAssignmentIDs), isGradeValid);
    }
    private Assignment getAssignmentFromCursor(Cursor cursor) {
        long db_id = cursor.getLong(cursor.getColumnIndex(DatabaseContract.DolphinColumns._ID));
        String title = cursor.getString(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_TITLE));
        double weight = cursor.getDouble(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_WEIGHT));
        double grade = cursor.getDouble(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_GRADE));
        long timeSpent = cursor.getLong(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_TIMESPENT));
        long parentDB_ID = cursor.getLong(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_PARENT_ID));
        boolean isGradeValid = cursor.getInt(cursor.getColumnIndex(DatabaseContract.DolphinColumns.COLUMN_GRADE_VALIDITY)) > 0;
        return new Assignment(db_id, parentDB_ID, timeSpent, grade, weight, title, isGradeValid);
    }
    public String listStringify(List<Long> listToStringify) {
        String stringifiedList = null;
        if (listToStringify != null) {
            for (long value : listToStringify) {
                if (stringifiedList == null) {
                    stringifiedList = Long.toString(value);
                } else {
                    stringifiedList = stringifiedList + "," + Long.toString(value);
                }
            }
        }
        return stringifiedList;
    }
    public List<Long> listUnstringify(String stringList) {
        List<Long> UnstringifiedList = new LinkedList<>();
        if (stringList != null) {
            String[] parsedString = stringList.split(",");
            for (int i = 0; i < parsedString.length; i++) {
                long convertedString = Long.parseLong(parsedString[i]);
                UnstringifiedList.add(convertedString);
            }
        }
        return UnstringifiedList;
    }

    //Assumes that the item is already in the database
    private void rippleDBAfterUpdating(Category category) {
        double weightSum = 0;
        double grade = 0;
        long time = 0;
        boolean isValid = false;

        for(Category cat : getAllCategoriesForClassID(category.getParentDB_ID())) {
            if(cat.isGradeValid()) {
                isValid = true;
                weightSum += cat.getWeight();
                grade += (cat.getWeight() * cat.getGrade());
                time += cat.getTimeSpentMillis();
            }
        }

        if(isValid)
            grade /= weightSum;

        Class _class = getClassByID(category.getParentDB_ID());

        _class.setGradeValidity(isValid);
        _class.setGrade(grade);
        _class.setTimeSpentMillis(time);

        putClass(_class);
    }
    private void rippleDBAfterUpdating(Assignment assignment) {
        double weightSum = 0;
        double grade = 0;
        long time = 0;
        boolean isValid = false;

        for(Assignment assign : getAllAssignmentsForCategoryID(assignment.getParentDB_ID())) {
            if(assign.isGradeValid()) {
                isValid = true;
                weightSum += assign.getWeight();
                grade += (assign.getWeight() * assign.getGrade());
                time += assign.getTimeSpentMillis();
            }
        }

        if(isValid)
            grade /= weightSum;

        Category category = getCategoryByID(assignment.getParentDB_ID());

        category.setGradeValidity(isValid);
        category.setGrade(grade);
        category.setTimeSpentMillis(time);

        putCategory(category);

        rippleDBAfterUpdating(category);
    }
    //Assumes that the item has already been deleted from the database
    private void prepDBToRemoveClass(final long DB_ID) {
        for(Category category : getAllCategoriesForClassID(DB_ID))
            removeCategoryByID(category.getDB_ID());
    }
    private void prepDBToRemoveCategory(final long DB_ID) {
        for(Assignment assignment : getAllAssignmentsForCategoryID(DB_ID))
            removeAssignmentByID(assignment.getDB_ID());
    }

    private DolphinSQLiteOpenHelper openHelper;

    private final String[] classColumns, categoryColumns, assignmentColumns;
}
