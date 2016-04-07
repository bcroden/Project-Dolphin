package com.projectdolphin.data.model;

/**
 * Holds all of the information for a specific assignment
 *
 * @author Alex
 */
public class Assignment extends GradedDBItem {
    public Assignment(long DB_ID, long CATEGORY_DB_ID, long timeSpentMillis, double grade, double weight, String title) {
        super(DB_ID, timeSpentMillis, grade, weight, title);
        this.CATEGORY_DB_ID = CATEGORY_DB_ID;
    }

    public final long getCATEGORY_DB_ID() {
        return CATEGORY_DB_ID;
    }

    @Override
    public String getWeightAsString() {
        return String.format("%.2f%%", getWeight());
    }

    private final long CATEGORY_DB_ID;
}
