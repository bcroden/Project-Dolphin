package com.projectdolphin.data.databases;

import android.provider.BaseColumns;

public final class CategoryDatabaseContract {

    public static final int    DATABASE_VERSION    = 1;
    public static final String DATABASE_NAME       = "earthquakes.db";
    public static final String TEXT_TYPE           = " TEXT";
    public static final String COMMA_SEP           = ", ";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty private constructor.
    private CategoryDatabaseContract() {}

    /* Inner class that defines the table contents */
    public static abstract class ItemColumns implements BaseColumns {

        public static final String TABLE_NAME   = "dolphinTable";
        public static final String COLUMN_TITLE   = "title";
        public static final String COLUMN_GRADE = "grade";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_TIMESPENT  = "timeSpent";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + _ID + " INTEGER PRIMARY KEY, "
                        + COLUMN_TITLE + TEXT_TYPE + COMMA_SEP
                        + COLUMN_GRADE + " DOUBLE not null" + COMMA_SEP
                        + COLUMN_WEIGHT + TEXT_TYPE + COMMA_SEP
                        + COLUMN_TIMESPENT + TEXT_TYPE + ")";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}