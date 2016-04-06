package com.projectdolphin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.projectdolphin.R;
import com.projectdolphin.data.Home;
import com.projectdolphin.layout.view.ListItem;

import java.util.LinkedList;
import java.util.List;

public class SelectItemToEditActivity extends AppCompatActivity {

    public static final String DATA_LEVEL_INTENT_KEY = "SELECT_ITEM_TO_EDIT_DATA_LEVEL_INTENT_KEY";

    public enum DataLevel { CLASS, CATEGORY }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_item_to_edit);

        dataLevel = DataLevel.valueOf(getIntent().getStringExtra(DATA_LEVEL_INTENT_KEY));
        List<String> items = new LinkedList<>();
        for(ListItem item : Home.getClassListItems())
            items.add(item.getTitle());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        ListView listView = (ListView) findViewById(R.id.select_item_to_edit_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(getItemClickListener());
    }
    
    private AdapterView.OnItemClickListener getItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (dataLevel) {
                    case CLASS:
                        Toast.makeText(view.getContext(), "Class Edit", Toast.LENGTH_SHORT).show();
                        break;
                    case CATEGORY:
                        Toast.makeText(view.getContext(), "Category Edit", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    private DataLevel dataLevel;
}
