package com.projectdolphin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.projectdolphin.layout.ListItem;
import com.projectdolphin.layout.ListItemAdapter;
import com.projectdolphin.R;
import com.projectdolphin.layout.ThreeFABMenu;
import com.projectdolphin.model.Home;

import java.util.LinkedList;
import java.util.List;

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
        new ThreeFABMenu(this, (ViewGroup) findViewById(R.id.home_layout));

        //TODO: call ThreeFABMenu's other functions to initialize the onClick listeners once they are added

        /* Initialize the List View */
        items = new LinkedList<>();
        adapter = new ListItemAdapter(this, R.layout.list_item, items);

        ListView listView = (ListView) findViewById(R.id.list_view);

        //Listener for when list items are clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = ((TextView) view.findViewById(R.id.title)).getText().toString() + " at pos=" + position;
                Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
            }
        });

        listView.setAdapter(adapter);
        items.addAll(Home.getClassListItems()); //load the information from somewhere
        adapter.notifyDataSetChanged();
    }

    private List<ListItem> items;
    private ArrayAdapter<ListItem> adapter;
}
