package com.projectdolphin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.projectdolphin.R;
import com.projectdolphin.data.database.DBAccessHelper;
import com.projectdolphin.data.model.Assignment;
import com.projectdolphin.data.model.Category;
import com.projectdolphin.layout.fab.ThreeFABMenu;
import com.projectdolphin.layout.view.DBListItem;
import com.projectdolphin.layout.view.ListItemRecycleAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Display all of the assignments in a particular category
 */

public class AssignmentViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //mandatory stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_view);

        //add the FloatingActionButton menu
        threeFABMenu = new ThreeFABMenu(this, (ViewGroup) findViewById(R.id.assignment_view_layout));
        threeFABMenu.setAddFABOnClickListener(getAddFABOnClickListener());
        threeFABMenu.setEditFABOnClickListener(getEditFABOnClickListener());
        threeFABMenu.setDeleteFABOnClickListener(getDeleteFABOnClickListener());

        categoryID = getIntent().getLongExtra(DBAccessHelper.CATEGORY_DB_ID_INTENT_KEY, -1);
        List<Assignment> assignments = new LinkedList<>();
        assignments.addAll(DBAccessHelper.getInstance(getApplicationContext()).getAllAssignmentsForCategoryID(categoryID));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.assignment_view_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ListItemRecycleAdapter recycleAdapter = new ListItemRecycleAdapter(assignments, getCardOnClickListener());
        recyclerView.setAdapter(recycleAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        threeFABMenu.emergencyHideMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.assignment_view_menu, menu);
        menu.getItem(0).setTitle(
                DBAccessHelper.getInstance(getApplicationContext())
                .getCategoryByID(categoryID)
                .getTitle()
        );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.assignment_view_menu_view_classes)
            startActivity(new Intent(this, ClassViewActivity.class));
        else if(item.getItemId() == R.id.assignment_view_menu_view_categories) {
            Intent intent = new Intent(this, CategoryViewActivity.class);
            Category category = DBAccessHelper.getInstance(getApplicationContext()).getCategoryByID(categoryID);
            intent.putExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, category.getParentDB_ID());
            startActivity(intent);
        }
        return false;
    }

    private View.OnClickListener getAddFABOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssignmentViewActivity.this, SaveAssignmentActivity.class);
                intent.putExtra(DBAccessHelper.CATEGORY_DB_ID_INTENT_KEY, categoryID);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener getEditFABOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssignmentViewActivity.this, SelectItemToModifyActivity.class);
                intent.putExtra(SelectItemToModifyActivity.SELECT_ITEM_TO_MODIFY_DATA_LEVEL_INTENT_KEY, SelectItemToModifyActivity.DataLevel.ASSIGNMENT.toString());
                intent.putExtra(SelectItemToModifyActivity.SELECT_ITEM_TO_MODIFY_ACTION_MODE_INTENT_KEY, SelectItemToModifyActivity.ActionMode.EDIT.toString());
                intent.putExtra(DBAccessHelper.CATEGORY_DB_ID_INTENT_KEY, categoryID);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener getDeleteFABOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssignmentViewActivity.this, SelectItemToModifyActivity.class);
                intent.putExtra(SelectItemToModifyActivity.SELECT_ITEM_TO_MODIFY_DATA_LEVEL_INTENT_KEY, SelectItemToModifyActivity.DataLevel.ASSIGNMENT.toString());
                intent.putExtra(SelectItemToModifyActivity.SELECT_ITEM_TO_MODIFY_ACTION_MODE_INTENT_KEY, SelectItemToModifyActivity.ActionMode.DELETE.toString());
                intent.putExtra(DBAccessHelper.CATEGORY_DB_ID_INTENT_KEY, categoryID);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener getCardOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = ((TextView)v.findViewById(R.id.view_card_title)).getText().toString();
                String id = ((TextView) v.findViewById(R.id.view_card_db_id)).getText().toString();

                Intent intent = new Intent(AssignmentViewActivity.this, TimerActivity.class);
                intent.putExtra(TimerActivity.ACTIVITY_TITLE, title);
                intent.putExtra(TimerActivity.ACTIVITY_ID, id);

                startActivity(intent);
            }
        };
    }

    private long categoryID;
    private ThreeFABMenu threeFABMenu;
}
