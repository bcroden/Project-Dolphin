package com.projectdolphin.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

import com.projectdolphin.data.model.Assignment;
import com.projectdolphin.data.model.GradedDBItem;

import java.util.List;

/**
 * Helper methods for performing error checking on user input
 */
public class SaveErrorChecker {
    public static void processTitle(GradedDBItem item, EditText titleText, List<String> errors) {
        String title = titleText.getText().toString();
        if("".equals(title))
            errors.add("Please enter a title");
        else if(title.length() > 18)
            errors.add("Title must be less than 18 characters");
        else
            item.setTitle(title);
    }
    public static void processWeight(GradedDBItem item, EditText weightText, List<String> errors) {
        try {
            double weight = Double.parseDouble(weightText.getText().toString());
            if(weight > 0)
                item.setWeight(weight);
            else
                errors.add("Weight must be a positive number");
        } catch(NumberFormatException e) {
            errors.add("Weight must be a number");
        }
    }
    public static void processGrade(GradedDBItem item, EditText gradeText, List<String> errors) {
        try {
            double weight = Double.parseDouble(gradeText.getText().toString());
            item.setGrade(weight);
        } catch(NumberFormatException e) {
            errors.add("Grade must be a number");
        }
    }
    public static void processTimeSpentMillis(GradedDBItem item, EditText timeText, List<String> errors) {
        try {
            long timeSpentMillis = Long.parseLong(timeText.getText().toString());
            if(timeSpentMillis < 0)
                errors.add("Time spent must be a positive number");
            else
                item.setTimeSpentMillis(timeSpentMillis);
        } catch(NumberFormatException e) {
            errors.add("Grade must be a number");
        }
    }
    public static boolean shouldContinue(List<String> errors, Activity activity) {
        if(errors.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < errors.size(); i++) {
                if(i != 0)
                    sb.append("\n");
                sb.append(errors.get(i));
            }

            new AlertDialog.Builder(activity)
                    .setMessage(sb.toString())
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
            return false;

        }

        return true;
    }
}
