package com.projectdolphin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.projectdolphin.layout.ClassRecycleAdapter;
import com.projectdolphin.layout.lists.expandable.ExpandableListAdapter;
import com.projectdolphin.layout.lists.expandable.ListItem;
import com.projectdolphin.R;
import com.projectdolphin.layout.ThreeFABMenu;
import com.projectdolphin.data.Home;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Home activity for displaying all of the classes
 */
public class HomeActivity extends AppCompatActivity {

    public static final String TAG = "Dolphin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //mandatory stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //auto-generated stuff to setup the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //add the FloatingActionButton menu
        ThreeFABMenu fabMenu = new ThreeFABMenu(this, (ViewGroup) findViewById(R.id.home_layout));
        fabMenu.setAddFABOnClickListener(getAddFABOnClickListener());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.view_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycleAdapter = new ClassRecycleAdapter(Home.getClassListItems());
        recyclerView.setAdapter(recycleAdapter);
    }

    private View.OnClickListener getAddFABOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Add FAB Clicked", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private List<ListItem> items;
    private ArrayAdapter<ListItem> adapter;
    private RecyclerView.Adapter recycleAdapter;
}
