package com.projectdolphin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Alex on 2/22/2016.
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
            TextView title = (TextView) view.findViewById(R.id.title);
            title.setText(item.getTitle());
            TextView grade = (TextView) view.findViewById(R.id.grade);
            grade.setText(item.getGrade());
            TextView timeSpent = (TextView) view.findViewById(R.id.time_spent);
            timeSpent.setText(item.getTimeSpent());
        }

        return view;
    }
}
