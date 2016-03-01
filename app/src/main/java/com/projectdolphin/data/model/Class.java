package com.projectdolphin.data.model;

import java.util.List;

/**
 * Holds all of the class level information
 *
 * @author Alex
 */
public class Class {

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    private List<Category> categories;
}
