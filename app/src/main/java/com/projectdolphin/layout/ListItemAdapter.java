package com.projectdolphin.layout;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.projectdolphin.R;

import java.util.List;

/**
 * Custom ArrayAdapter for ListItems
 *
 * @author Alex
 */
public class ListItemAdapter extends ArrayAdapter<ListItem> {
    public ListItemAdapter(Context context, int resource, List<ListItem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.view_list_item, null);
        }

        ListItem item = getItem(position);
        if(item != null) {
            double gradeValue = item.Grade();
            int alpha = (int) (255 * gradeValue);
            view.setBackgroundColor(Color.argb(alpha, 0, 230, 0));

//            TextView weight = (TextView) view.findViewById(R.id.view_list_item_weight);
//            weight.setText(item.Fat());
//            TextView title = (TextView) view.findViewById(R.id.view_list_item_title);
//            title.setText(item.Title());
//            TextView grade = (TextView) view.findViewById(R.id.view_list_item_grade);
//            grade.setText(String.format("%.2f%%", gradeValue*100));
//            TextView timeSpent = (TextView) view.findViewById(R.id.view_list_item_time_spent);
//            timeSpent.setText(item.TimeSpent());
        }

        return view;
    }
}
