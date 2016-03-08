package com.projectdolphin.layout;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.projectdolphin.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Alex on 3/7/2016.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    public ExpandableListAdapter(Context context, List<ListItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ListItemDisplayHelper.getInstance().getNumberOfPairs();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return items.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ListItem item = (ListItem) getGroup(groupPosition);
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expand_list_header, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.expandable_list_header);
        Button viewButton = (Button) convertView.findViewById(R.id.expandable_header_view_btn);
        viewButton.setFocusable(false);
        textView.setText(item.Title());
        double gradeValue = item.Grade();
        int alpha = (int) (255 * gradeValue);
        convertView.setBackgroundColor(Color.argb(alpha, 0, 230, 0));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ListItem item = (ListItem) getChild(groupPosition, childPosition);
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_list_item, null);
        }
        ListItemDisplayHelper.Pair pair = ListItemDisplayHelper.getInstance().getNthPair(childPosition, item);
        TextView keyView = (TextView) convertView.findViewById(R.id.expand_child_key);
        TextView valueView = (TextView) convertView.findViewById(R.id.expand_child_value);
        keyView.setText(pair.getKey());
        valueView.setText(pair.getValue());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private Context context;
    private List<ListItem> items;
}
