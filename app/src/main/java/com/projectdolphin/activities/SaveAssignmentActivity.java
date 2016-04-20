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
        setContentView(R.layout.activity_save_assignment);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //toDoDB.close();
    }

    //Get the content
    public void onAddAssignment(View view) {
        EditText myWeight = (EditText) findViewById(R.id.class_weight);
        EditText myTitle = (EditText) findViewById(R.id.class_title);

        String weight = myWeight.getText().toString();
        String title = myTitle.getText().toString();

        Long longTimeSpentForDb = 0L;//Long.parseLong(timeSpent);
        Double floatGradeForDB = 0.0;//Double.parseDouble(grade);
        Double floatWeightForDB = Double.parseDouble(weight);
        List<Integer> assignmentIds = null;
        long categoryID = 1; //Get the category Id from the intent

        //Need to udpate the parent categories' list of assignments here
        DBAccessHelper.getInstance(getApplicationContext()).putAssignment(new Assignment(categoryID, longTimeSpentForDb, floatGradeForDB, floatWeightForDB, title));

    }

}
