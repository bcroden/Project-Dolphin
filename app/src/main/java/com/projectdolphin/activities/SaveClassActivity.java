package com.projectdolphin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.projectdolphin.R;
import com.projectdolphin.data.database.ClassSQLiteOpenHelper;
import com.projectdolphin.data.database.DBAccessHelper;
import com.projectdolphin.data.model.Class;

import java.util.List;

public class SaveClassActivity extends AppCompatActivity {

    private ClassSQLiteOpenHelper classHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_class);
        classHelper = new ClassSQLiteOpenHelper(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //toDoDB.close();
    }

    //Get the content
    public void onAddClass(View view) {
        //Cursor cursor = null;
        EditText myTimeSpent = (EditText) findViewById((R.id.class_timeSpent));
        EditText myGrade = (EditText) findViewById((R.id.class_grade));
        EditText myWeight = (EditText) findViewById(R.id.class_weight);
        EditText myTitle = (EditText) findViewById(R.id.class_title);

        String timeSpent = myTimeSpent.getText().toString();
        String grade = myGrade.getText().toString();
        String weight = myWeight.getText().toString();
        String title = myTitle.getText().toString();

        Long longTimeSpentForDb = Long.parseLong(timeSpent);
        Double floatGradeForDB = Double.parseDouble(grade);
        Double floatWeightForDB = Double.parseDouble(weight);
        List<Integer> categoryIds = null;
        categoryIds.add(1);


        classHelper.insert(DBAccessHelper.getInstance(getApplicationContext()), new Class(longTimeSpentForDb, floatGradeForDB, floatWeightForDB, title, categoryIds));

//        cursor = dolphinDB.query(DatabaseContract.DolphinColumns.CLASS_TABLE_NAME,
//                new String[] {DatabaseContract.DolphinColumns.COLUMN_TITLE, DatabaseContract.DolphinColumns.COLUMN_GRADE,
//                        DatabaseContract.DolphinColumns.COLUMN_WEIGHT, DatabaseContract.DolphinColumns.COLUMN_TIMESPENT},
//                "title = ? AND grade = ? AND weight = ? AND timeSpent = ?",
//                new String[]{title, grade, weight, timeSpent}, null, null, null);

    }

}
