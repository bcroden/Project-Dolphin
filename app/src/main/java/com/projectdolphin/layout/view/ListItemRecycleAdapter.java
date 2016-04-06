package com.projectdolphin.layout.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.projectdolphin.R;

import java.util.List;

/**
 * Following http://developer.android.com/training/material/lists-cards.html#RecyclerView
 * @author Alex
 */
public class ListItemRecycleAdapter extends android.support.v7.widget.RecyclerView.Adapter<ListItemRecycleAdapter.ViewHolder> {
    public ListItemRecycleAdapter(List<ListItem> data) {
        this.data = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView titleText, gradeText, timeText, weightText;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            titleText = (TextView) itemView.findViewById(R.id.view_card_title);
            gradeText = (TextView) itemView.findViewById(R.id.view_card_grade);
            timeText = (TextView) itemView.findViewById(R.id.view_card_time);
            weightText = (TextView) itemView.findViewById(R.id.view_card_weight);
            imageView = (ImageView) itemView.findViewById(R.id.view_card_image);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), titleText.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public ListItemRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem item = data.get(position);
        holder.titleText.setText(item.getTitle());
        holder.gradeText.setText(String.format("%.2f%%", item.getGrade()));
        holder.timeText.setText(item.getTimeSpentAsString());
        holder.weightText.setText(item.getWeight());
        holder.imageView.setImageResource(R.drawable.dolphin);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private List<ListItem> data;
}