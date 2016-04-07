package com.projectdolphin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.projectdolphin.R;
import com.projectdolphin.data.Home;
import com.projectdolphin.data.database.DBAccessHelper;
import com.projectdolphin.layout.view.DBListItem;

import java.util.LinkedList;
import java.util.List;

public class SelectItemToEditActivity extends AppCompatActivity {

    public static final String SELECT_ITEM_TO_EDIT_DATA_LEVEL_INTENT_KEY = "SELECT_ITEM_TO_EDIT_DATA_LEVEL_INTENT_KEY";

    public enum DataLevel { CLASS, CATEGORY, ASSIGNMENT }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_item_to_edit);

        dataLevel = DataLevel.valueOf(getIntent().getStringExtra(SELECT_ITEM_TO_EDIT_DATA_LEVEL_INTENT_KEY));
        items = new LinkedList<>();
        List<String> dummyItems = new LinkedList<>();
        for(DBListItem item : Home.getClassListItems()) {
            items.add(item);
            dummyItems.add(item.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dummyItems);
        ListView listView = (ListView) findViewById(R.id.select_item_to_edit_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(getItemClickListener());
    }
    
    private AdapterView.OnItemClickListener getItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (dataLevel) {
                    case CLASS:
                        intent = new Intent(SelectItemToEditActivity.this, SaveClassActivity.class);
                        intent.putExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, items.get(position).getDB_ID());
                        break;
                    case CATEGORY:
                        intent = new Intent(SelectItemToEditActivity.this, SaveCategoryActivity.class);
                        intent.putExtra(DBAccessHelper.CATEGORY_DB_ID_INTENT_KEY, items.get(position).getDB_ID());
                        break;
                    default:
                        intent = new Intent(SelectItemToEditActivity.this, SaveAssignmentActivity.class);
                        intent.putExtra(DBAccessHelper.ASSIGNMENT_DB_ID_INTENT_KEY, items.get(position).getDB_ID());
                        break;
                }
                startActivity(intent);
            }
        };
    }

    private DataLevel dataLevel;
    private List<DBListItem> items;
}
