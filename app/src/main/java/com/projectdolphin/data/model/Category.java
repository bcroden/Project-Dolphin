package com.projectdolphin.data.model;

import com.projectdolphin.layout.view.ListItem;

import java.util.List;

/**
 * Holds all of the information for a specific category
 *
 * Note: methods and members should to be added as needed
 *
 * @author Alex
 */
public class Category implements ListItem {
    String Title;
    public double Grade;
    String Weight;
    String TimeSpent;

    public Category(){}

    public Category(String title, double grade, String weight, String timeSpent){
        Title = title;
        Grade = grade;
        Weight = weight;
        TimeSpent = timeSpent;
    }



    @Override
    public String getTitle() {
        //return null;
        return Title;
    }

    @Override
    public double getGrade() {
        //return 0.954;
        return Grade;
    }

    @Override
    public String getWeight() {
        //return null;
        return Weight;
    }

    @Override
    public String getTimeSpentAsString() {
        //return null;
        return TimeSpent;
    }

    public void setTitle(String title){
        Title = title;
    }

    public void setGrade(Double grade){
        Grade = grade;
    }



    public void setWeight(String weight){
        Weight = weight;
    }

    public void setTimeSpentAsString(String timeSpent){
        TimeSpent = timeSpent;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    private List<Assignment> assignments;

    @Override
    public String toString() {
        return "Title:\t" + Title + " Grade:\t" + Grade + "\nWeight:\t" + Weight + " TimeSpent:\t" + TimeSpent;
    }
}