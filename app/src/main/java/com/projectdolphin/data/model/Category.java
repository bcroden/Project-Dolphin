package com.projectdolphin.data.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Holds all of the information for a specific category
 *
 * @author Alex
 */
public class Category extends GradedDBItem {

    public Category(long parDB_id, String title, double weight) {
        this(parDB_id, 0, 1.0, weight, title, new LinkedList<Long>());
    }
    public Category(long PARENT_DB_ID, long timeSpentMillis, double grade, double weight, String title, List<Long> assignmentsIDs) {
        this(-1, PARENT_DB_ID, timeSpentMillis, grade, weight, title, assignmentsIDs);
    }
    public Category(long DB_ID, long PARENT_DB_ID, long timeSpentMillis, double grade, double weight, String title, List<Long> assignmentsIDs) {
        super(DB_ID, timeSpentMillis, grade, weight, title);
        this.PARENT_DB_ID = PARENT_DB_ID;
        this.assignmentsIDs = assignmentsIDs;
    }

    public final long getParentDB_ID() {
        return PARENT_DB_ID;
    }

    @Override
    public String getWeightAsString() {
        return String.format("%.2f%%", getWeight());
    }

    public List<Long> getAssignmentsIDs() {
        return assignmentsIDs;
    }
    public void setAssignmentsIDs(List<Long> assignmentsIDs) {
        this.assignmentsIDs = assignmentsIDs;
    }

    private final long PARENT_DB_ID;
    private List<Long> assignmentsIDs;

    @Override
    public String toString() {
        return "Title:\t" + getTitle() + " Grade:\t" + getGrade() + "\nWeight:\t" + getWeight() + " TimeSpent:\t" + getTimeSpentAsString();
    }
}