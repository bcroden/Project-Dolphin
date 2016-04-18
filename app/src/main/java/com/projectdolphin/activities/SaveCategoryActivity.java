package com.projectdolphin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.projectdolphin.R;
import com.projectdolphin.data.database.DBAccessHelper;
import com.projectdolphin.data.model.Category;

import java.util.LinkedList;
import java.util.List;

public class SaveCategoryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_category);

        /* Pseudo code by Alex showing how he would do things if he was Ryan START */

        //if intent contains a Category DB ID {

            //We are now in edit/update mode

            //Ask the DB Access Helper to get the Category we need by using the ID we were given

            //Save what the DB Access Helper gave us in a member variable

        //} else if intent contains a Class DB ID {

            //We are now in add/insert mode

            //Save the parent id to a member variable

        //} else both of the above were false then something way worse has happened and I don't know how to fix it

        /* Pseudo code by Alex showing how he would do things if he was Ryan END */
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
        List<Long> assignmentIds = new LinkedList<>(); //empty because it doesn't have classes yet
        long classID = 1; //long classID = getExtra(Class ID)

        /* Pseudo code by Alex showing how he would do things if he was Ryan START */

        //if we are in edit/update mode {

            //use the setter methods of the Category member variable

            //use the putCategory method of the DB Access Helper class

        //} else if we are in add/insert mode {

            //create new Category object

            //use the putCategory method of the DB Access Helper class

            //get parent class using DB Access Helper getClassByID method

            //update list of class's categories using the setter/getters

            //use the putClass method of the DB Access Helper class

        //}

        /*
         * Note: The put* methods implement some logic that automatically determines if the item should be inserted or updated.
         */

        /* Pseudo code by Alex showing how he would do things if he was Ryan END */

        //Need to update parent class' list of categories here
        DBAccessHelper.getInstance(getApplicationContext()).putCategory(new Category(classID, longTimeSpentForDb, floatGradeForDB, floatWeightForDB, title, assignmentIds));

    }

}