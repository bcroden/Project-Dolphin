package com.projectdolphin.data;

import com.projectdolphin.layout.view.DBListItem;

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
     *  @return list of DBListItem objects by which the overall class stats will be displayed
     */
    public static List<DBListItem> getClassListItems() {
        List<DBListItem> items = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            DBListItem item = new DBListItem() {
                @Override
                public String getWeightAsString() {
                    return "4 hours";
                }

                @Override
                public long getDB_ID() {
                    return -1;
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
