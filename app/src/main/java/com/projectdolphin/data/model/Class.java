package com.projectdolphin.data.model;

import com.projectdolphin.layout.ListItem;

import java.util.List;

/**
 * Holds all of the class level information
 *
 * Note: methods and members should to be added as needed
 *
 * @author Alex
 */
public class Class implements ListItem {

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getGradeAsString() {
        return null;
    }

    @Override
    public String getWeightAsString() {
        return null;
    }

    @Override
    public String getTimeSpentAsString() {
        return null;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    private List<Category> categories;
}
