package com.projectdolphin.data.model;

import java.util.List;

/**
 * Holds all of the information for a specific category
 *
 * @author Alex
 */
public class Category {

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    private List<Assignment> assignments;
}
