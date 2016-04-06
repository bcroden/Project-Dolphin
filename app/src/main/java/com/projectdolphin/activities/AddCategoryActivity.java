package com.projectdolphin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.projectdolphin.R;
import com.projectdolphin.data.databases.CategoryDatabaseContract;
import com.projectdolphin.data.databases.CategorySQLiteOpenHelper;
import com.projectdolphin.data.model.Category;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.projectdolphin.data.databases.CategoryDatabaseContract.ItemColumns.*;

public class AddCategoryActivity extends AppCompatActivity {

    private ArrayList<Category> categoryList;
    private ArrayAdapter<Category> categoryAdapter;

    private CategorySQLiteOpenHelper categoryHelper;
    private SQLiteDatabase categoryDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        categoryList = new ArrayList<>();
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                categoryList);
        ListView categoryListView = (ListView) findViewById(R.id.categoryListView);
        categoryListView.setAdapter(categoryAdapter);
        categoryHelper = new CategorySQLiteOpenHelper(this);
        categoryDB = categoryHelper.getWritableDatabase();
        categoryHelper.init(categoryDB);
        //This starts my second activity
       categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
//                Intent intent = new Intent(AddCategoryActivity.this, AddAssignmentActivity.class);
//                Category categoryToBePassed = categoryList.get(position);
//                String data = categoryToBePassed.Title() + "," + categoryToBePassed.Grade() + "," + categoryToBePassed.Weight() + "," + caregoryToBePassed.TimeSpent();
//                intent.putExtra("Data", data);
//                startActivity(intent);
            }
        });
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

        cursor = categoryDB.query(CategoryDatabaseContract.ItemColumns.TABLE_NAME,
                new String[] {CategoryDatabaseContract.ItemColumns.COLUMN_TITLE, CategoryDatabaseContract.ItemColumns.COLUMN_GRADE,
                        CategoryDatabaseContract.ItemColumns.COLUMN_WEIGHT, CategoryDatabaseContract.ItemColumns.COLUMN_TIMESPENT},
                "title = ? AND grade = ? AND weight = ? AND timeSpent = ?",
                new String[]{title, grade, weight, timeSpent}, null, null, null);

        if (cursor !=null && cursor.getCount() > 0){
            cursor.moveToFirst();
            categoryList.clear();
            while (!cursor.isAfterLast()) {
                Category category = new Category();
                category.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                category.setGrade(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_GRADE)));
                category.setWeight(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEIGHT)));
                category.setTimeSpentAsString(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESPENT)));
                categoryList.add(category);
                cursor.moveToNext();
            }
            categoryAdapter.notifyDataSetChanged();
            cursor.close();
        }
        else{
            categoryList.clear();
            categoryAdapter.notifyDataSetChanged();
        }
    }


    //Delete everything from the database, clear the listView, notify the listview
    public void onClear(View view){
        categoryHelper.delete(categoryDB);
        categoryList.clear();
        categoryAdapter.notifyDataSetChanged();
    }
}