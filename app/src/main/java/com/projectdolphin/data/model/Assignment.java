package com.projectdolphin.data.model;

/**
 * Holds all of the information for a specific assignment
 *
 * @author Alex
 */
public class Assignment extends GradedDBItem {
    public Assignment(long parDB_id, String title, double weight) {
        this(parDB_id, 0, 1.0, weight, title, false);
    }
    public Assignment(long PARENT_DB_ID, long timeSpentMillis, double grade, double weight, String title, boolean isGradeValid) {
        this(-1, PARENT_DB_ID, timeSpentMillis, grade, weight, title, isGradeValid);
    }
    public Assignment(long DB_ID, long PARENT_DB_ID, long timeSpentMillis, double grade, double weight, String title, boolean isGradeValid) {
        super(DB_ID, timeSpentMillis, grade, weight, title, isGradeValid);
        this.PARENT_DB_ID = PARENT_DB_ID;
    }

    public final long getParentDB_ID() {
        return PARENT_DB_ID;
    }

    @Override
    public String getWeightAsString() {
        return String.format("%.2f", getWeight());
    }

    private final long PARENT_DB_ID;
}
