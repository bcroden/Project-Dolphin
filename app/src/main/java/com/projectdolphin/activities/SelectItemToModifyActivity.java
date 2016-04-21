package com.projectdolphin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.projectdolphin.R;
import com.projectdolphin.data.database.DBAccessHelper;
import com.projectdolphin.data.model.Assignment;
import com.projectdolphin.data.model.Category;
import com.projectdolphin.layout.view.DBListItem;

import java.util.LinkedList;
import java.util.List;

public class SelectItemToModifyActivity extends AppCompatActivity {

    public static final String SELECT_ITEM_TO_MODIFY_DATA_LEVEL_INTENT_KEY = "SELECT_ITEM_TO_MODIFY_DATA_LEVEL_INTENT_KEY";
    public static final String SELECT_ITEM_TO_MODIFY_ACTION_MODE_INTENT_KEY = "SELECT_ITEM_TO_MODIFY_ACTION_MODE_INTENT_KEY";

    public enum ActionMode { EDIT, DELETE }
    public enum DataLevel { CLASS, CATEGORY, ASSIGNMENT }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_item_to_edit);

        actionMode = ActionMode.valueOf(getIntent().getStringExtra(SELECT_ITEM_TO_MODIFY_ACTION_MODE_INTENT_KEY));
        dataLevel = DataLevel.valueOf(getIntent().getStringExtra(SELECT_ITEM_TO_MODIFY_DATA_LEVEL_INTENT_KEY));
        items = new LinkedList<>();
        switch (dataLevel) {
            case CLASS:
                items.addAll(DBAccessHelper.getInstance(getApplicationContext()).getAllClasses());
                break;
            case CATEGORY:
                long classID = getIntent().getLongExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, -1);
                items.addAll(DBAccessHelper.getInstance(getApplicationContext()).getAllCategoriesForClassID(classID));
                break;
            case ASSIGNMENT:
                long categoryID = getIntent().getLongExtra(DBAccessHelper.CATEGORY_DB_ID_INTENT_KEY, -1);
                items.addAll(DBAccessHelper.getInstance(getApplicationContext()).getAllAssignmentsForCategoryID(categoryID));
                break;
        }

        List<String> itemTitles = new LinkedList<>();
        for(DBListItem item : items)
            itemTitles.add(item.getTitle());
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemTitles);
        ListView listView = (ListView) findViewById(R.id.select_item_to_edit_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(getItemClickListener());
    }
    
    private AdapterView.OnItemClickListener getItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (actionMode) {
                    case DELETE:
                        intent = getDeleteIntentForItem(position);
                        break;
                    default:
                        intent = getEditIntentForItem(position);
                        break;
                }
                startActivity(intent);
            }

            private Intent getEditIntentForItem(int position) {
                Intent intent;
                switch (dataLevel) {
                    case CLASS:
                        intent = new Intent(SelectItemToModifyActivity.this, SaveClassActivity.class);
                        intent.putExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, items.get(position).getDB_ID());
                        break;
                    case CATEGORY:
                        intent = new Intent(SelectItemToModifyActivity.this, SaveCategoryActivity.class);
                        intent.putExtra(DBAccessHelper.CATEGORY_DB_ID_INTENT_KEY, items.get(position).getDB_ID());
                        break;
                    default:
                        intent = new Intent(SelectItemToModifyActivity.this, SaveAssignmentActivity.class);
                        intent.putExtra(DBAccessHelper.ASSIGNMENT_DB_ID_INTENT_KEY, items.get(position).getDB_ID());
                        break;
                }
                return intent;
            }
            private Intent getDeleteIntentForItem(int position) {
                long id = items.get(position).getDB_ID();
                Intent intent;
                switch (dataLevel) {
                    case CLASS:
                        intent = new Intent(SelectItemToModifyActivity.this, ClassViewActivity.class);
                        DBAccessHelper.getInstance(getApplicationContext()).removeClassByID(id);
                        break;
                    case CATEGORY:
                        intent = new Intent(SelectItemToModifyActivity.this, CategoryViewActivity.class);
                        intent.putExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, ((Category)items.get(position)).getParentDB_ID());
                        DBAccessHelper.getInstance(getApplicationContext()).removeCategoryByID(id);
                        break;
                    default:
                        intent = new Intent(SelectItemToModifyActivity.this, AssignmentViewActivity.class);
                        intent.putExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, ((Assignment)items.get(position)).getParentDB_ID());
                        DBAccessHelper.getInstance(getApplicationContext()).removeAssignmentByID(id);
                        break;
                }
                return intent;
            }
        };
    }

    private ActionMode actionMode;
    private DataLevel dataLevel;
    private List<DBListItem> items;
}
