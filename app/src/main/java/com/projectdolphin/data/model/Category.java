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

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public double getGrade() {
        return 0.954;
    }

    @Override
    public String getWeight() {
        return null;
    }

    @Override
    public String getTimeSpentAsString() {
        return null;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    private List<Assignment> assignments;
}
