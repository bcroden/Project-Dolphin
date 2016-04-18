package com.projectdolphin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.projectdolphin.R;
import com.projectdolphin.data.database.DBAccessHelper;
import com.projectdolphin.data.model.Assignment;

import java.util.List;

public class SaveAssignmentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_assignment);;
    }

    @Override
    protected void onStop() {
        super.onStop();
        //toDoDB.close();
    }

    //Get the content
    public void onAddAssignment(View view) {
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
        List<Integer> assignmentIds = null;
        long categoryID = 1; //Get the category Id from the intent

        //Need to udpate the parent categories' list of assignments here
        DBAccessHelper.getInstance(getApplicationContext()).insertAssignment(new Assignment(categoryID, longTimeSpentForDb, floatGradeForDB, floatWeightForDB, title));

    }

}
