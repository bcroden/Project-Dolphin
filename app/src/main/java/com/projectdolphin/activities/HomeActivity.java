package com.projectdolphin.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.projectdolphin.layout.ExpandableListAdapter;
import com.projectdolphin.layout.ListItem;
import com.projectdolphin.layout.ListItemAdapter;
import com.projectdolphin.R;
import com.projectdolphin.layout.ThreeFABMenu;
import com.projectdolphin.data.Home;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Home activity for displaying all of the classes
 */
public class HomeActivity extends AppCompatActivity {

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

        List<String> headers = Arrays.asList("Batman", "Captain America", "Johnny", "Suzy");
        Map<String, List<String>> data = new HashMap<>();
        for(String header : headers) {
            List<String> list = Arrays.asList("nana", "nana", "nana");
            data.put(header, list);
        }

        ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(this, Home.getClassListItems());
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandable_list_view);
        expandableListView.setAdapter(expandableListAdapter);


        /* Initialize the List View */
        /*items = new LinkedList<>();
        adapter = new ListItemAdapter(this, R.layout.view_list_item, items);

        ListView listView = (ListView) findViewById(R.id.expandable_list_view);

        //Listener for when list items are clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = ((TextView) view.findViewById(R.id.view_list_item_title)).getText().toString() + " at pos=" + position;
                Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, "LongClick", Snackbar.LENGTH_SHORT).show();
                int visibility = view.findViewById(R.id.view_list_item_hidden).getVisibility();
                if(visibility != View.VISIBLE)
                    view.findViewById(R.id.view_list_item_hidden).setVisibility(View.VISIBLE);
                else
                    view.findViewById(R.id.view_list_item_hidden).setVisibility(View.GONE);
                return true;
            }
        });

        listView.setAdapter(adapter);
        items.addAll(Home.getClassListItems()); //load the information from somewhere
        adapter.notifyDataSetChanged();*/
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
}
