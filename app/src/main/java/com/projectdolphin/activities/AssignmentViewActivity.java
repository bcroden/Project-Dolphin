package com.projectdolphin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.projectdolphin.R;
import com.projectdolphin.data.database.DBAccessHelper;
import com.projectdolphin.data.model.Assignment;
import com.projectdolphin.layout.fab.ThreeFABMenu;
import com.projectdolphin.layout.view.DBListItem;
import com.projectdolphin.layout.view.ListItemRecycleAdapter;

import java.util.LinkedList;
import java.util.List;

public class AssignmentViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //mandatory stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_view);

        //add the FloatingActionButton menu
        ThreeFABMenu fabMenu = new ThreeFABMenu(this, (ViewGroup) findViewById(R.id.assignment_view_layout));
        fabMenu.setAddFABOnClickListener(getAddFABOnClickListener());
        fabMenu.setEditFABOnClickListener(getEditFABOnClickListener());

        List<Assignment> assignments = new LinkedList<>();
        categoryID = getIntent().getLongExtra(DBAccessHelper.CATEGORY_DB_ID_INTENT_KEY, -1);
        assignments.addAll(DBAccessHelper.getInstance(getApplicationContext()).getAllCategoriesForCategoryID(categoryID));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.assignment_view_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycleAdapter = new ListItemRecycleAdapter(assignments, getCardOnClickListener());
        recyclerView.setAdapter(recycleAdapter);
    }

    private View.OnClickListener getAddFABOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssignmentViewActivity.this, SaveAssignmentActivity.class);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener getEditFABOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssignmentViewActivity.this, SelectItemToEditActivity.class);
                intent.putExtra(SelectItemToEditActivity.SELECT_ITEM_TO_EDIT_DATA_LEVEL_INTENT_KEY, SelectItemToEditActivity.DataLevel.ASSIGNMENT.toString());
                intent.putExtra(DBAccessHelper.CATEGORY_DB_ID_INTENT_KEY, categoryID);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener getCardOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Hello", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private long categoryID;
    private List<DBListItem> items;
    private ArrayAdapter<DBListItem> adapter;
    private RecyclerView.Adapter recycleAdapter;
}
