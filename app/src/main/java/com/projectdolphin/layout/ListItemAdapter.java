package com.projectdolphin.layout;

import android.content.Context;
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
            view = inflater.inflate(R.layout.list_item, null);
        }

        ListItem item = getItem(position);
        if(item != null) {
            TextView weight = (TextView) view.findViewById(R.id.weight);
            weight.setText(item.getWeightAsString());
            TextView title = (TextView) view.findViewById(R.id.title);
            title.setText(item.getTitle());
            TextView grade = (TextView) view.findViewById(R.id.grade);
            grade.setText(item.getGradeAsString());
            TextView timeSpent = (TextView) view.findViewById(R.id.time_spent);
            timeSpent.setText(item.getTimeSpentAsString());
        }

        return view;
    }
}
