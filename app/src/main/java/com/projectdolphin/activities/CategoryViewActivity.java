package com.projectdolphin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projectdolphin.R;
import com.projectdolphin.data.database.DBAccessHelper;
import com.projectdolphin.data.model.Category;
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
        threeFABMenu = new ThreeFABMenu(this, (ViewGroup) findViewById(R.id.category_view_layout));
        threeFABMenu.setAddFABOnClickListener(getAddFABOnClickListener());
        threeFABMenu.setEditFABOnClickListener(getEditFABOnClickListener());
        threeFABMenu.setDeleteFABOnClickListener(getDeleteFABOnClickListener());

        classID = getIntent().getLongExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, -1);
        List<Category> categories = new LinkedList<>();
        categories.addAll(DBAccessHelper.getInstance(getApplicationContext()).getAllCategoriesForClassID(classID));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.category_view_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter recycleAdapter = new ListItemRecycleAdapter(categories, getCardOnClickListener());
        recyclerView.setAdapter(recycleAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        threeFABMenu.emergencyHideMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_view_menu, menu);
        menu.getItem(0).setTitle(
                DBAccessHelper.getInstance(getApplicationContext())
                        .getClassByID(classID)
                        .getTitle()
        );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.category_view_menu_view_class)
            startActivity(new Intent(this, ClassViewActivity.class));
        return false;
    }

    private View.OnClickListener getAddFABOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryViewActivity.this, SaveCategoryActivity.class);
                intent.putExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, classID);
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
                intent.putExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, classID);
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
                intent.putExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, classID);
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

    private long classID;
    private ThreeFABMenu threeFABMenu;
}
