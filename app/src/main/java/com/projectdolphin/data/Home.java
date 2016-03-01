package com.projectdolphin.data;

import com.projectdolphin.layout.ListItem;

import java.util.LinkedList;
import java.util.List;

/**
 * Helper object for HomeActivity
 *
 * @author Alex
 */
public class Home {
    /**
     *  Get class data from DB or file
     *
     *  @return list of ListItem objects by which the overall class stats will be displayed
     */
    public static List<ListItem> getClassListItems() {
        List<ListItem> items = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            ListItem item = new ListItem() {
                @Override
                public String getWeight() {
                    return "4 hours";
                }

                @Override
                public String getTitle() {
                    return "Dummy Title";
                }

                @Override
                public String getGrade() {
                    return "95.4%";
                }

                @Override
                public String getTimeSpent() {
                    return "32:16:08";
                }
            };
            items.add(item);
        }
        return items;
    }
}
