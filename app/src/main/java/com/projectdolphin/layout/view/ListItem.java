package com.projectdolphin.layout.view;

/**
 * An attempt to abstract classes, categories, and assignments when they
 * are displayed in a list view.
 *
 * @author Alex
 */
public interface ListItem {
    public String getTitle();
    public double getGrade();
    public String getWeightAsString();
    public String getTimeSpentAsString();
}
