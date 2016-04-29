package com.projectdolphin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.projectdolphin.R;
import com.projectdolphin.data.database.DBAccessHelper;
import com.projectdolphin.data.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class predictDesiredGrade extends AppCompatActivity {

    long db_id;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    ArrayList<String> values;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict_desired_grade);

        db_id = getIntent().getLongExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, -1);

        // get the listview
        ListView listView = (ListView) findViewById(R.id.gradePredictions);
        values = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);


        // setting list adapter
        listView.setAdapter(adapter);

    }

    public void onPredict(View view){
        adapter.clear();
        EditText desiredGradeEditText = (EditText) findViewById(R.id.desiredGrade);
        double desiredClassGrade = 0.0;
        try {
            desiredClassGrade = Double.parseDouble(desiredGradeEditText.getText().toString());
        }catch(NumberFormatException e){
            Toast toast = Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT);
            toast.show();
        }

        ClassData classData = DBAccessHelper.getInstance(getApplicationContext()).getAllDataFor(db_id);
        if(db_id != -1) {
            com.projectdolphin.data.model.Class classCalculatingFor = classData.getClassInfo();
            Map<Category, List<Assignment>> categoryAssignmentMap = classData.getCategoryAssignmentMap();
            if(categoryAssignmentMap != null && categoryAssignmentMap.size() != 0) {
                double currentClassGrade = classCalculatingFor.getGrade();

                //double pointsNeededForGrade = desiredClassGrade - currentClassGrade;

                double totalPoints = 0.0;
                double currentPoints = 0.0;
                ArrayList<PredictDisplayObject> allAssignments = new ArrayList<PredictDisplayObject>();
                ArrayList<PredictDisplayObject> allKnownAssignments = new ArrayList<PredictDisplayObject>();
                ArrayList<PredictDisplayObject> allUnknownAssignments = new ArrayList<PredictDisplayObject>();
                ArrayList<PredictDisplayObject> Categories = new ArrayList<PredictDisplayObject>();

                for (Map.Entry<Category, List<Assignment>> entry : categoryAssignmentMap.entrySet()) {
                    Category keyCategory = entry.getKey();
                    List<Assignment> valueList = entry.getValue();

                    //Compute the Categories' grade/minute
                    double gradePerMin = keyCategory.getGrade() / (((keyCategory.getTimeSpentMillis()) / 1000) / 60);

                    for (Assignment assignment : valueList) {
                        PredictDisplayObject displayAssignment = new PredictDisplayObject();
                        displayAssignment.title = assignment.getTitle();
                        displayAssignment.parentCategory = keyCategory.getTitle();
                        displayAssignment.gradesPerMinute = gradePerMin;
                        displayAssignment.weightInClass = (assignment.getWeight() * keyCategory.getWeight()) / 100;

                        //Calculate the total points in the entire class from the assignment level
                        if (totalPoints == 0) {
                            totalPoints = assignment.getWeight();
                        } else {
                            totalPoints = totalPoints + assignment.getWeight();
                        }

                        //Add the assignment to the appropriate list based on if the grade is known
                        if (assignment.isGradeValid() == false) {
                            allUnknownAssignments.add(displayAssignment);
                            allAssignments.add(displayAssignment);
                        } else {
                            if (currentPoints == 0) {
                                currentPoints = (keyCategory.getWeight()/100)*((assignment.getGrade()/100)*assignment.getWeight());
                            } else {
                                currentPoints = currentPoints + (keyCategory.getWeight()/100)*((assignment.getGrade()/100)*assignment.getWeight());

                            }
                            allKnownAssignments.add(displayAssignment);
                            allAssignments.add(displayAssignment);
                        }
                    }
                }

                //double pointsNeededForGrade = desiredClassGrade - ((currentPoints / totalPoints) * 100.0);
                double pointsNeededForGrade = desiredClassGrade - currentPoints;

                //If we won't already get the grade by getting all 0's
                if (pointsNeededForGrade > 0) {
                    //Sort the list by grades per min
                    allUnknownAssignments = rankByGradePerMin(allUnknownAssignments);

                    //Calculate predicted grade for each assignment
                    for (int i = 0; i < allUnknownAssignments.size(); i++) {
                        PredictDisplayObject assignmentToDo = allUnknownAssignments.get(i);

                        if (pointsNeededForGrade > assignmentToDo.weightInClass) {
                            allUnknownAssignments.get(i).predictedGrade = assignmentToDo.weightInClass;
                            pointsNeededForGrade = pointsNeededForGrade - assignmentToDo.weightInClass;
                        } else {
                            allUnknownAssignments.get(i).predictedGrade = pointsNeededForGrade;
                            pointsNeededForGrade = pointsNeededForGrade - assignmentToDo.weightInClass;
                            pointsNeededForGrade = 0;
                        }

                    }
                    HashMap<String, Integer> isCategory = new HashMap<>();
                    //For each assignment calculate minutes needed studying and link it to its parent category
                    for (int i = 0; i < allUnknownAssignments.size(); i++) {
                        PredictDisplayObject tempCategory = new PredictDisplayObject();
                        PredictDisplayObject tempAssignment = allUnknownAssignments.get(i);
                        double minutesStudying = ((tempAssignment.predictedGrade / tempAssignment.weightInClass) * 100) / tempAssignment.gradesPerMinute;
                        allUnknownAssignments.get(i).minutesStudying = minutesStudying;
                        tempCategory.title = tempAssignment.parentCategory;
                        tempCategory.minutesStudying = tempAssignment.minutesStudying;
                        tempCategory.predictedGrade = tempAssignment.predictedGrade;
                        tempCategory.index = i;
                        if (!isCategory.containsKey(tempCategory.title)) {
                            isCategory.put(tempCategory.title, tempCategory.index);
                            Categories.add(tempCategory);
                        } else {
                            Categories.get(isCategory.get(tempCategory.title)).minutesStudying =
                                    Categories.get(isCategory.get(tempCategory.title)).minutesStudying + tempAssignment.minutesStudying;
                            Categories.get(isCategory.get(tempCategory.title)).predictedGrade =
                                    Categories.get(isCategory.get(tempCategory.title)).predictedGrade + tempAssignment.predictedGrade;
                        }
                    }

                    //For each category and assignment, add them to the list view
                    if((!(pointsNeededForGrade != 0 || desiredClassGrade < 0) || desiredClassGrade < 101) && pointsNeededForGrade <= 0) {
                        for (PredictDisplayObject tempCat : Categories) {
                            values.add("Category: " + tempCat.title + "\nTotal Time Studying: " + (int) tempCat.minutesStudying + " minutes");
                            for (PredictDisplayObject tempAssign : allUnknownAssignments) {
                                if (tempAssign.parentCategory == tempCat.title) {
                                    values.add("     Assignment: " + tempAssign.title + "\n     Time Studying: " + (int) tempAssign.minutesStudying
                                            + " minutes\n     Expected Grade: " + (int) ((tempAssign.predictedGrade / tempAssign.weightInClass) * 100));
                                }
                            }
                        }
                    } else {
                        values.add("Your desired Grade cannot be reached");
                    }
                }else{
                    values.add("You do not have to do any more work in order to get your desired grade");
                }
                adapter.notifyDataSetChanged();
            }else{
                values.add("You do not have any information saved for your class");
                adapter.notifyDataSetChanged();
            }
        }else{
            values.add("It seems that your class was not saved properly. Please delete and try again");
            adapter.notifyDataSetChanged();
        }
    }

    public ArrayList<PredictDisplayObject> rankByGradePerMin(ArrayList<PredictDisplayObject> unrankedList){
        ArrayList<PredictDisplayObject> rankedList = new ArrayList<PredictDisplayObject>();
        while(unrankedList.size() != 0) {
            double maxGradePerMin = 0.0;
            int maxPosition = 0;
            for (int i = 0; i < unrankedList.size(); i++) {
                PredictDisplayObject temp = unrankedList.get(i);
                if(maxGradePerMin == 0 ){
                    maxGradePerMin = temp.gradesPerMinute; maxPosition = i;
                }else if(maxGradePerMin < temp.gradesPerMinute){
                    maxGradePerMin = temp.gradesPerMinute;
                    maxPosition = i;
                }
            };
            rankedList.add(unrankedList.get(maxPosition));
            unrankedList.remove(maxPosition);
        }
        return rankedList;
    }

}
