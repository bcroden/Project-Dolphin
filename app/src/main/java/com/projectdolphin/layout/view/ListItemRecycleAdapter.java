package com.projectdolphin.layout.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.projectdolphin.R;

import java.util.List;

/**
 * Following http://developer.android.com/training/material/lists-cards.html#RecyclerView
 * @author Alex
 */
public class ListItemRecycleAdapter extends android.support.v7.widget.RecyclerView.Adapter<ListItemRecycleAdapter.ViewHolder> {
    public ListItemRecycleAdapter(List<? extends DBListItem> data, View.OnClickListener onClickListener) {
        this.data = data;
        this.onClickListener = onClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleText, gradeText, timeText, weightText, hiddenDB_ID;
        public ViewHolder(View itemView, View.OnClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(listener);
            titleText = (TextView) itemView.findViewById(R.id.view_card_title);
            gradeText = (TextView) itemView.findViewById(R.id.view_card_grade);
            timeText = (TextView) itemView.findViewById(R.id.view_card_time);
            weightText = (TextView) itemView.findViewById(R.id.view_card_weight);
            imageView = (ImageView) itemView.findViewById(R.id.view_card_image);
            hiddenDB_ID = (TextView) itemView.findViewById(R.id.view_card_db_id);
        }
    }

    @Override
    public ListItemRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card, parent, false);
        return new ViewHolder(v, onClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DBListItem item = data.get(position);
        holder.titleText.setText(item.getTitle());
        holder.gradeText.setText(String.format("%.2f", item.getGrade()));
        holder.timeText.setText(item.getTimeSpentAsString());
        holder.weightText.setText(item.getWeightAsString());
        holder.imageView.setImageResource(R.drawable.dolphin);
        holder.hiddenDB_ID.setText(Long.toString(item.getDB_ID()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private List<? extends DBListItem> data;
    private View.OnClickListener onClickListener;
}
