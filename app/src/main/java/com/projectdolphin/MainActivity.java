package com.projectdolphin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ListItem> items;
    private ArrayAdapter<ListItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new LinkedList<>();
        items.add(new ListItem() {
            @Override
            public String getTitle() {
                return "Dummy Title";
            }

            @Override
            public String getGrade() {
                return "95.4%";
            }

            @Override
            public String getTimeSpent() {
                return "32:16:08";
            }
        });
        adapter = new ListItemAdapter(this, R.layout.list_item, items);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
