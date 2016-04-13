package com.projectdolphin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.projectdolphin.R;
import com.projectdolphin.data.database.CategorySQLiteOpenHelper;
import com.projectdolphin.data.database.DBAccessHelper;
import com.projectdolphin.data.model.Category;

import java.util.List;

public class SaveAssignmentActivity extends AppCompatActivity {

    private CategorySQLiteOpenHelper assignmentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_assignment);
        assignmentHelper = new CategorySQLiteOpenHelper(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //toDoDB.close();
    }

    //Get the content
    public void onAddCategory(View view) {
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
        long classID = 1;
        categoryIds.add(1);

        assignmentHelper.insert(DBAccessHelper.getInstance(getApplicationContext()), new Category(classID, longTimeSpentForDb, floatGradeForDB, floatWeightForDB, title, categoryIds));

    }

}
