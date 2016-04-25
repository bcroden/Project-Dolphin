package com.projectdolphin.data.model;

import java.util.List;
import java.util.Map;

/**
 * Data access object which holds all of the data for a particular class
 */
public class ClassData {

    public ClassData(Class _class, Map<Category, List<Assignment>> categoryAssignmentMap) {
        this._class = _class;
        this.categoryAssignmentMap = categoryAssignmentMap;
    }

    public Class getClassInfo() {
        return _class;
    }
    public Map<Category, List<Assignment>> getCategoryAssignmentMap() {
        return categoryAssignmentMap;
    }

    private Class _class;
    private Map<Category, List<Assignment>> categoryAssignmentMap;
}
