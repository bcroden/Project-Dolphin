package com.projectdolphin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.projectdolphin.R;
import com.projectdolphin.data.database.DatabaseContract;
import com.projectdolphin.data.database.CategorySQLiteOpenHelper;
import com.projectdolphin.data.model.Category;

import java.util.ArrayList;

public class SaveCategoryActivity extends AppCompatActivity {

    private ArrayList<Category> categoryList;
    private ArrayAdapter<Category> categoryAdapter;

    private CategorySQLiteOpenHelper categoryHelper;
    private SQLiteDatabase categoryDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_category);

        categoryHelper = new CategorySQLiteOpenHelper(this);
        categoryDB = categoryHelper.getWritableDatabase();
        categoryHelper.init(categoryDB);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //toDoDB.close();
    }

    //Get the content
    public void onAddCategory(View view) {
        Cursor cursor = null;
        EditText myTitle = (EditText) findViewById(R.id.category_title);
        EditText myGrade = (EditText) findViewById((R.id.category_grade));
        EditText myWeight = (EditText) findViewById(R.id.category_weight);
        EditText myTimeSpent = (EditText) findViewById((R.id.category_timeSpent));

        String title = myTitle.getText().toString();
        String grade = myGrade.getText().toString();
        String weight = myWeight.getText().toString();
        String timeSpent = myTimeSpent.getText().toString();

        Double floatGradeForDB = Double.parseDouble(grade);

        categoryHelper.insert(categoryDB, new Category(title, floatGradeForDB, weight, timeSpent));

        cursor = categoryDB.query(DatabaseContract.CategoryColumns.TABLE_NAME,
                new String[] {DatabaseContract.CategoryColumns.COLUMN_TITLE, DatabaseContract.CategoryColumns.COLUMN_GRADE,
                        DatabaseContract.CategoryColumns.COLUMN_WEIGHT, DatabaseContract.CategoryColumns.COLUMN_TIMESPENT},
                "title = ? AND grade = ? AND weight = ? AND timeSpent = ?",
                new String[]{title, grade, weight, timeSpent}, null, null, null);

    }

}