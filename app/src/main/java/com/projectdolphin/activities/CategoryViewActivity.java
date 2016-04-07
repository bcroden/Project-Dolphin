package com.projectdolphin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.projectdolphin.R;
import com.projectdolphin.data.Home;
import com.projectdolphin.layout.fab.ThreeFABMenu;
import com.projectdolphin.layout.view.DBListItem;
import com.projectdolphin.layout.view.ListItemRecycleAdapter;

import java.util.List;

/**
 * Home activity for displaying all of the classes
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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.category_view_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycleAdapter = new ListItemRecycleAdapter(Home.getClassListItems(), getCardOnClickListener());
        recyclerView.setAdapter(recycleAdapter);
    }

    private View.OnClickListener getAddFABOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryViewActivity.this, SaveCategoryActivity.class);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener getEditFABOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryViewActivity.this, SelectItemToEditActivity.class);
                intent.putExtra(SelectItemToEditActivity.DATA_LEVEL_INTENT_KEY, SelectItemToEditActivity.DataLevel.CATEGORY.toString());
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener getCardOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryViewActivity.this, AssignmentViewActivity.class);
                startActivity(intent);
            }
        };
    }

    private List<DBListItem> items;
    private ArrayAdapter<DBListItem> adapter;
    private RecyclerView.Adapter recycleAdapter;
}
