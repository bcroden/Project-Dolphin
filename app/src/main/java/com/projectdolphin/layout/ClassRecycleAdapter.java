package com.projectdolphin.layout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projectdolphin.R;

/**
 * Following http://developer.android.com/training/material/lists-cards.html#RecyclerView
 * @author Alex
 */
public class ClassRecycleAdapter extends android.support.v7.widget.RecyclerView.Adapter<ClassRecycleAdapter.ViewHolder> {
    public ClassRecycleAdapter(String[] data) {
        this.data = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.info_text);
        }
    }

    @Override
    public ClassRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    private String[] data;
}
