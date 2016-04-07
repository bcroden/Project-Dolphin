package com.projectdolphin.data.database;

import android.content.Context;

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

    public static final String CLASS_DB_ID_INTENT_KEY = "CLASS_DB_ID_INTENT_KEY";
    public static final String CATEGORY_DB_ID_INTENT_KEY = "CATEGORY_DB_ID_INTENT_KEY";
    public static final String ASSIGNMENT_DB_ID_INTENT_KEY = "ASSIGNMENT_DB_ID_INTENT_KEY";

    public static DBAccessHelper getInstance(Context context) {
        if(instance == null)
            instance = new DBAccessHelper(context);
        return instance;
    }
    private DBAccessHelper(Context context) {
        openHelper = new CategorySQLiteOpenHelper(context.getApplicationContext());
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
        //TODO: Implement this using the openHelper
    }

    /* Assignment methods */
    public List<Assignment> getAllCategoriesForCategoryID(long CATEGORY_DB_ID) {
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
    public void insertAssignment(Assignment category) {
        //TODO: Implement this using the openHelper
    }

    private CategorySQLiteOpenHelper openHelper;
}
