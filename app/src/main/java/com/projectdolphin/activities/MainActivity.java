package com.projectdolphin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.projectdolphin.layout.ListItem;
import com.projectdolphin.layout.ListItemAdapter;
import com.projectdolphin.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Activity to try out displaying list of information
 */
public class MainActivity extends AppCompatActivity {

    private List<ListItem> items;
    private ArrayAdapter<ListItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new LinkedList<>();
        adapter = new ListItemAdapter(this, R.layout.list_item, items);

        ListView listView = (ListView) findViewById(R.id.list_view);

        //Listener for when list items are clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = ((TextView) view.findViewById(R.id.title)).getText().toString();
                Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
            }
        });

        listView.setAdapter(adapter);
        items.addAll(getItems());
        adapter.notifyDataSetChanged();

    }

    public List<ListItem> getItems() {
        ListItem item = new ListItem() {
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
        };
        List<ListItem> items = new LinkedList<>();
        items.add(item);
        return items;
    }
}
