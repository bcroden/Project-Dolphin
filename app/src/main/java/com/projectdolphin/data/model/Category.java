package com.projectdolphin.data.model;

import com.projectdolphin.layout.ListItem;

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
    public String Title() {
        return null;
    }

    @Override
    public double Grade() {
        return 0.954;
    }

    @Override
    public String Fat() {
        return null;
    }

    @Override
    public String TimeSpent() {
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
