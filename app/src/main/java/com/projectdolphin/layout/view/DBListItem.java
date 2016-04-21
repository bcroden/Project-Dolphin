package com.projectdolphin.layout.view;

/**
 * An attempt to abstract classes, categories, and assignments when they
 * are displayed in a list view.
 *
 * @author Alex
 */
public interface DBListItem {
    public long getDB_ID();
    public String getTitle();
    public double getGrade();
    public String getWeightAsString();
    public String getTimeSpentAsString();

    boolean isGradeValid();
}
