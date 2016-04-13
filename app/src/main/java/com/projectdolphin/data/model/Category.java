package com.projectdolphin.data.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Holds all of the information for a specific category
 *
 * @author Alex
 */
public class Category extends GradedDBItem {

    public Category(long CLASS_DB_ID, long timeSpentMillis, double grade, double weight, String title, List<Integer> assignmentsIDs) {
        this(-1, CLASS_DB_ID, timeSpentMillis, grade, weight, title, assignmentsIDs);
    }
    public Category(long DB_ID, long CLASS_DB_ID, long timeSpentMillis, double grade, double weight, String title, List<Integer> assignmentsIDs) {
        super(DB_ID, timeSpentMillis, grade, weight, title);
        this.CLASS_DB_ID = CLASS_DB_ID;
        this.assignmentsIDs = assignmentsIDs;
    }

//    @Deprecated
//    public Category(String title, double grade, String weight, String timeSpent){
//        super(new Random().nextLong(), new Random().nextDouble(), new Random().nextDouble(), "Deprecated Title");
//        this.CLASS_DB_ID = -1;
//        this.assignmentsIDs = new LinkedList<>();
//    }

    public final long getCLASS_DB_ID() {
        return CLASS_DB_ID;
    }

    @Override
    public String getWeightAsString() {
        return String.format("%.2f%%", getWeight());
    }

    public List<Integer> getAssignmentsIDs() {
        return assignmentsIDs;
    }
    public void setAssignmentsIDs(List<Integer> assignmentsIDs) {
        this.assignmentsIDs = assignmentsIDs;
    }

    private final long CLASS_DB_ID;
    private List<Integer> assignmentsIDs;

    @Override
    public String toString() {
        return "Title:\t" + getTitle() + " Grade:\t" + getGrade() + "\nWeight:\t" + getWeight() + " TimeSpent:\t" + getTimeSpentAsString();
    }
}