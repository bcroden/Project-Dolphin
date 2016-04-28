package com.projectdolphin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

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

        CheckBox checkBox = ((CheckBox) findViewById(R.id.save_assignment_is_grade_valid_box));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    findViewById(R.id.save_assignment_grade_entry_form).setVisibility(View.VISIBLE);
                else
                    findViewById(R.id.save_assignment_grade_entry_form).setVisibility(View.GONE);
            }
        });

        long assignDB_ID = getIntent().getLongExtra(DBAccessHelper.ASSIGNMENT_DB_ID_INTENT_KEY, -1);
        long parDB_ID = getIntent().getLongExtra(DBAccessHelper.CATEGORY_DB_ID_INTENT_KEY, -1);
        if(assignDB_ID >= 0) {
            isEditMode = true;
            assignment = DBAccessHelper.getInstance(getApplicationContext()).getAssignmentByID(assignDB_ID);
            ((EditText) findViewById(R.id.save_assignment_title_field)).setText(assignment.getTitle());
            ((EditText) findViewById(R.id.save_assignment_grade_field)).setText(String.format("%.2f", assignment.getGrade()));
            ((EditText) findViewById(R.id.save_assignment_weight_field)).setText(assignment.getWeightAsString());
            ((EditText) findViewById(R.id.save_assignment_time_field)).setText(Long.toString(assignment.getTimeSpentMillis() / MILLIS_PER_MINUTE));
            checkBox.setChecked(assignment.isGradeValid());
        } else if(parDB_ID >= 0) {
            isEditMode = false;
            assignment = new Assignment(parDB_ID, "Empty Assignment", 1.0);
        }
    }

    public void onAddAssignment(View view) {

        List<String> errors = new LinkedList<>();

        boolean isValid = ((CheckBox) findViewById(R.id.save_assignment_is_grade_valid_box)).isChecked();
        assignment.setGradeValidity(isValid);

        SaveErrorChecker.processTitle(assignment, (EditText) findViewById(R.id.save_assignment_title_field), errors);
        SaveErrorChecker.processWeight(assignment, (EditText) findViewById(R.id.save_assignment_weight_field), errors);
        if(isValid)
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

    private final int MILLIS_PER_MINUTE = 60 * 1000;
    private boolean isEditMode;
    private Assignment assignment;
}
