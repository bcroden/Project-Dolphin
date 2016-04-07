package com.projectdolphin.data;

import com.projectdolphin.layout.view.ListItem;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Helper object for ClassViewActivity
 *
 * @author Alex
 */
@Deprecated
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
                public String getWeightAsString() {
                    return "4 hours";
                }

                @Override
                public String getTitle() {
                    return "Dummy Title";
                }

                @Override
                public double getGrade() {
                    return new Random().nextDouble();
                }

                @Override
                public String getTimeSpentAsString() {
                    return "32:16:08";
                }
            };
            items.add(item);
        }
        return items;
    }
}
