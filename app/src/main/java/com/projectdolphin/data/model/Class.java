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
    public String Title() {
        return null;
    }

    @Override
    public double Grade() {
        return 0.954;
    }

    @Override
    public String Fat() {
        return null;
    }

    @Override
    public String TimeSpent() {
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
