package com.projectdolphin.data.model;

import com.projectdolphin.layout.view.DBListItem;

/**
 * Attributes common to classes, categories, and assignments
 */
public abstract class GradedDBItem implements DBListItem {
    public GradedDBItem(long timeSpentMillis, double grade, double weight, String title) {
        this(-1, timeSpentMillis, grade, weight, title);
    }
    public GradedDBItem(long DB_ID, long timeSpentMillis, double grade, double weight, String title) {
        this.DB_ID = DB_ID;
        this.timeSpentMillis = timeSpentMillis;
        this.grade = grade;
        this.weight = weight;
        this.title = title;
    }

    /**
     * @return True if this item needs to be inserted into the database
     */
    public boolean needsToBeInserted() {
        return DB_ID < 0;
    }
    public final long getDB_ID() {
        return DB_ID;
    }

    @Override
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public double getGrade() {
        return grade;
    }
    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String getTimeSpentAsString() {
        long hrs = timeSpentMillis / MILLIS_IN_HR;
        long min = (timeSpentMillis - (hrs*MILLIS_IN_HR)) / (60 * 1000);
        long sec = (timeSpentMillis - (hrs*MILLIS_IN_HR) - (min*MILLIS_IN_MIN)) / 1000;
        return String.format("%d:%d:%d", hrs, min, sec);
    }
    public long getTimeSpentMillis() {
        return timeSpentMillis;
    }
    public void setTimeSpentMillis(long timeSpentMillis) {
        this.timeSpentMillis = timeSpentMillis;
    }

    private final long DB_ID;
    private long timeSpentMillis;
    private double grade, weight;
    private String title;

    private static final long MILLIS_IN_SEC = 1000;
    private static final long MILLIS_IN_MIN = 60 * MILLIS_IN_SEC;
    private static final long MILLIS_IN_HR = 60 * MILLIS_IN_MIN;
}
