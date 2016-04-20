package com.projectdolphin.data.model;

import java.util.List;

/**
 * Holds all of the class level information
 *
 * @author Alex
 */
public class Class extends GradedDBItem {

    public Class(long timeSpentMillis, double grade, double weight, String title, List<Long> categoryIDs) {
        this(-1, timeSpentMillis, grade, weight, title, categoryIDs);
    }
    public Class(long DB_ID, long timeSpentMillis, double grade, double weight, String title, List<Long> categoryIDs) {
        super(DB_ID, timeSpentMillis, grade, weight, title);
        this.categoryIDs = categoryIDs;
    }

    @Override
    public String getWeightAsString() {
        return String.format("%.0f hours", getWeight());
    }

    public List<Long> getCategoryIDs() {
        return categoryIDs;
    }
    public void setCategoryIDs(List<Long> categoryIDs) {
        this.categoryIDs = categoryIDs;
    }

    private List<Long> categoryIDs;
}
