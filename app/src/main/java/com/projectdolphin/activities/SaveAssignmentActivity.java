package com.projectdolphin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.projectdolphin.R;
import com.projectdolphin.data.database.DBAccessHelper;
import com.projectdolphin.data.model.Assignment;
import com.projectdolphin.data.model.Category;

import java.util.LinkedList;
import java.util.List;

public class SaveAssignmentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_assignment);

        long assignDB_ID = getIntent().getLongExtra(DBAccessHelper.ASSIGNMENT_DB_ID_INTENT_KEY, -1);
        long parDB_ID = getIntent().getLongExtra(DBAccessHelper.CATEGORY_DB_ID_INTENT_KEY, -1);
        if(assignDB_ID >= 0) {
            isEditMode = true;
            assignment = DBAccessHelper.getInstance(getApplicationContext()).getAssignmentByID(assignDB_ID);
        } else if(parDB_ID >= 0) {
            isEditMode = false;
            assignment = new Assignment(parDB_ID, "Empty Assignment", 1.0);
        }
    }

    public void onAddAssignment(View view) {

        List<String> errors = new LinkedList<>();

        assignment.setTitle("foo");

        SaveErrorChecker.processTitle(assignment, (EditText) findViewById(R.id.save_assignment_title_field), errors);
        SaveErrorChecker.processWeight(assignment, (EditText) findViewById(R.id.save_assignment_weight_field), errors);
        SaveErrorChecker.processGrade(assignment, (EditText) findViewById(R.id.save_assignment_grade_field), errors);
        SaveErrorChecker.processTimeSpentMillis(assignment, (EditText) findViewById(R.id.save_assignment_time_field), errors);

        if(!SaveErrorChecker.shouldContinue(errors, this))
            return;

        //Need to udpate the parent categories' list of assignments here
        DBAccessHelper.getInstance(getApplicationContext()).putAssignment(assignment);

        if(!isEditMode) {
            Category category = DBAccessHelper.getInstance(getApplicationContext()).getCategoryByID(assignment.getParentDB_ID());
            category.getAssignmentsIDs().add(assignment.getDB_ID());
            DBAccessHelper.getInstance(getApplicationContext()).putCategory(category);
        }

        Intent intent = new Intent(this, AssignmentViewActivity.class);
        intent.putExtra(DBAccessHelper.CATEGORY_DB_ID_INTENT_KEY, assignment.getParentDB_ID());
        startActivity(intent);
    }

    private boolean isEditMode;
    private Assignment assignment;
}
