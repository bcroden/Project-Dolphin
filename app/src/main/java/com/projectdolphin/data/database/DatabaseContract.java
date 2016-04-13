package com.projectdolphin.data.database;

import android.provider.BaseColumns;

public final class DatabaseContract {

    public static final int    DATABASE_VERSION    = 1;
    public static final String DATABASE_NAME       = "dolphin.db";
    public static final String TEXT_TYPE           = " TEXT";
    public static final String COMMA_SEP           = ", ";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty private constructor.
    private DatabaseContract() {}

    /* Define the Category Table */
    public static abstract class DolphinColumns implements BaseColumns {

        public static final String CLASS_TABLE_NAME = "ClassTable";
        public static final String CATEGORY_TABLE_NAME = "CategoryTable";
        public static final String ASSIGNMENT_TABLE_NAME = "AssignmentTable";
        public static final String COLUMN_TITLE   = "title";
        public static final String COLUMN_GRADE = "grade";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_TIMESPENT  = "timeSpent";
        public static final String COLUMN_PARENT_ID = "parentId";

        public static final String CREATE_CLASS_TABLE =
                "CREATE TABLE " + CLASS_TABLE_NAME + " ("
                        + _ID + " INTEGER PRIMARY KEY, "
                        + COLUMN_TITLE + TEXT_TYPE + COMMA_SEP
                        + COLUMN_GRADE + " DOUBLE not null" + COMMA_SEP
                        + COLUMN_WEIGHT + TEXT_TYPE + COMMA_SEP
                        + COLUMN_TIMESPENT + TEXT_TYPE + COMMA_SEP + ")";

        public static final String CREATE_CATEGORY_TABLE =
                "CREATE TABLE " + CATEGORY_TABLE_NAME + " ("
                        + _ID + " INTEGER PRIMARY KEY, "
                        + COLUMN_TITLE + TEXT_TYPE + COMMA_SEP
                        + COLUMN_GRADE + " DOUBLE not null" + COMMA_SEP
                        + COLUMN_WEIGHT + TEXT_TYPE + COMMA_SEP
                        + COLUMN_TIMESPENT + TEXT_TYPE
                        + COLUMN_PARENT_ID + "INTEGER not null, " + ")";

        public static final String CREATE_ASSIGNMENT_TABLE =
                "CREATE TABLE " + ASSIGNMENT_TABLE_NAME + " ("
                        + _ID + " INTEGER PRIMARY KEY, "
                        + COLUMN_TITLE + TEXT_TYPE + COMMA_SEP
                        + COLUMN_GRADE + " DOUBLE not null" + COMMA_SEP
                        + COLUMN_WEIGHT + TEXT_TYPE + COMMA_SEP
                        + COLUMN_TIMESPENT + TEXT_TYPE
                        + COLUMN_PARENT_ID + "INTEGER not null, " + ")";


        public static final String DELETE_CLASS_TABLE = "DROP TABLE IF EXISTS " + CLASS_TABLE_NAME;
        public static final String DELETE_CATEGORY_TABLE = "DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME;
        public static final String DELETE_ASSIGNMENT_TABLE = "DROP TABLE IF EXISTS " + ASSIGNMENT_TABLE_NAME;
    }

}