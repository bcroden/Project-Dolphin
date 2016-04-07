package com.projectdolphin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.projectdolphin.R;
import com.projectdolphin.data.Home;
import com.projectdolphin.data.database.DBAccessHelper;
import com.projectdolphin.layout.fab.ThreeFABMenu;
import com.projectdolphin.layout.view.DBListItem;
import com.projectdolphin.layout.view.ListItemRecycleAdapter;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Home activity for displaying all of the classes
 */
public class ClassViewActivity extends AppCompatActivity {

    public static final String CLASS_DB_ID_INTENT_KEY = "CLASS_DB_ID_INTENT_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //mandatory stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_view);

        //auto-generated stuff to setup the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //add the FloatingActionButton menu
        ThreeFABMenu fabMenu = new ThreeFABMenu(this, (ViewGroup) findViewById(R.id.class_view_layout));
        fabMenu.setAddFABOnClickListener(getAddFABOnClickListener());
        fabMenu.setEditFABOnClickListener(getEditFABOnClickListener());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.view_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycleAdapter = new ListItemRecycleAdapter(Home.getClassListItems(), getCardOnClickListener());
        recyclerView.setAdapter(recycleAdapter);
    }

    private View.OnClickListener getAddFABOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassViewActivity.this, SaveClassActivity.class);
                startActivity(intent);
            }
        };
    }


    private View.OnClickListener getEditFABOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassViewActivity.this, SelectItemToEditActivity.class);
                intent.putExtra(SelectItemToEditActivity.DATA_LEVEL_INTENT_KEY, SelectItemToEditActivity.DataLevel.CLASS.toString());
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener getCardOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassViewActivity.this, CategoryViewActivity.class);
                long db_id = Long.parseLong(((TextView) v.findViewById(R.id.view_card_db_id)).getText().toString());
                intent.putExtra(CLASS_DB_ID_INTENT_KEY, db_id);
                startActivity(intent);
            }
        };
    }

    private List<DBListItem> items;
    private ArrayAdapter<DBListItem> adapter;
    private RecyclerView.Adapter recycleAdapter;
}
