package com.projectdolphin.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.projectdolphin.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 3/7/2016.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    /**
     *
     * @param context
     * @param headers   List of headers
     * @param childData mapping from a header to a list of items under it
     */
    public ExpandableListAdapter(Context context, List<String> headers, Map<String, List<String>> childData) {
        this.context = context;
        this.headers = headers;
        this.childData = childData;
    }

    @Override
    public int getGroupCount() {
        return headers.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(headers.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headers.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(headers.get(groupPosition)).get(childPosition);
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
        String headerTitle = (String) getGroup(groupPosition);
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expand_list_header, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.expandable_list_header);
        Button viewButton = (Button) convertView.findViewById(R.id.expandable_header_view_btn);
        viewButton.setFocusable(false);
        textView.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childText = (String) getChild(groupPosition, childPosition);
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_list_item, null);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private Context context;
    private List<String> headers;
    private Map<String, List<String>> childData;
}
