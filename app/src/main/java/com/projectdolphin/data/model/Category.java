package com.projectdolphin.data.model;

import java.util.List;

/**
 * Holds all of the information for a specific category
 *
 * @author Alex
 */
public class Category extends GradedDBItem {

    public Category(long PARENT_DB_ID, long timeSpentMillis, double grade, double weight, String title, List<Integer> assignmentsIDs) {
        this(-1, PARENT_DB_ID, timeSpentMillis, grade, weight, title, assignmentsIDs);
    }
    public Category(long DB_ID, long PARENT_DB_ID, long timeSpentMillis, double grade, double weight, String title, List<Integer> assignmentsIDs) {
        super(DB_ID, timeSpentMillis, grade, weight, title);
        this.PARENT_DB_ID = PARENT_DB_ID;
        this.assignmentsIDs = assignmentsIDs;
    }

//    @Deprecated
//    public Category(String title, double grade, String weight, String timeSpent){
//        super(new Random().nextLong(), new Random().nextDouble(), new Random().nextDouble(), "Deprecated Title");
//        this.PARENT_DB_ID = -1;
//        this.assignmentsIDs = new LinkedList<>();
//    }

    public final long getParent_DB_ID() {
        return PARENT_DB_ID;
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

    private final long PARENT_DB_ID;
    private List<Integer> assignmentsIDs;

    @Override
    public String toString() {
        return "Title:\t" + getTitle() + " Grade:\t" + getGrade() + "\nWeight:\t" + getWeight() + " TimeSpent:\t" + getTimeSpentAsString();
    }
}