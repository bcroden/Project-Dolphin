package com.projectdolphin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projectdolphin.R;
import com.projectdolphin.data.database.DBAccessHelper;
import com.projectdolphin.data.model.Assignment;
import com.projectdolphin.data.model.Category;
import com.projectdolphin.data.model.Class;
import com.projectdolphin.layout.fab.ThreeFABMenu;
import com.projectdolphin.layout.view.ListItemRecycleAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Display all of the categories in particular class
 */
public class CategoryViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //mandatory stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);

        //add the FloatingActionButton menu
        ThreeFABMenu fabMenu = new ThreeFABMenu(this, (ViewGroup) findViewById(R.id.category_view_layout));
        fabMenu.setAddFABOnClickListener(getAddFABOnClickListener());
        fabMenu.setEditFABOnClickListener(getEditFABOnClickListener());
        fabMenu.setDeleteFABOnClickListener(getDeleteFABOnClickListener());

        List<Assignment> categories = new LinkedList<>();
        _class = DBAccessHelper.getInstance(getApplicationContext())
                                .getClassByID(getIntent().getLongExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, -1));
        categories.addAll(DBAccessHelper.getInstance(getApplicationContext()).getAllAssignmentsForCategoryID(_class.getDB_ID()));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.category_view_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter recycleAdapter = new ListItemRecycleAdapter(categories, getCardOnClickListener());
        recyclerView.setAdapter(recycleAdapter);
    }

    private View.OnClickListener getAddFABOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryViewActivity.this, SaveCategoryActivity.class);
                intent.putExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, _class.getDB_ID());
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener getEditFABOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryViewActivity.this, SelectItemToModifyActivity.class);
                intent.putExtra(SelectItemToModifyActivity.SELECT_ITEM_TO_MODIFY_DATA_LEVEL_INTENT_KEY, SelectItemToModifyActivity.DataLevel.CATEGORY.toString());
                intent.putExtra(SelectItemToModifyActivity.SELECT_ITEM_TO_MODIFY_ACTION_MODE_INTENT_KEY, SelectItemToModifyActivity.ActionMode.EDIT.toString());
                intent.putExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, _class.getDB_ID());
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener getDeleteFABOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryViewActivity.this, SelectItemToModifyActivity.class);
                intent.putExtra(SelectItemToModifyActivity.SELECT_ITEM_TO_MODIFY_DATA_LEVEL_INTENT_KEY, SelectItemToModifyActivity.DataLevel.CATEGORY.toString());
                intent.putExtra(SelectItemToModifyActivity.SELECT_ITEM_TO_MODIFY_ACTION_MODE_INTENT_KEY, SelectItemToModifyActivity.ActionMode.DELETE.toString());
                intent.putExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, _class.getDB_ID());
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener getCardOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryViewActivity.this, AssignmentViewActivity.class);
                long db_id = Long.parseLong(((TextView) v.findViewById(R.id.view_card_db_id)).getText().toString());
                intent.putExtra(DBAccessHelper.CATEGORY_DB_ID_INTENT_KEY, db_id);
                startActivity(intent);
            }
        };
    }

    private Class _class;
}
