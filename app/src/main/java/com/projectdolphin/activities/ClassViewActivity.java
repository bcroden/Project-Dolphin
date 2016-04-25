package com.projectdolphin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.projectdolphin.R;
import com.projectdolphin.data.database.DBAccessHelper;
import com.projectdolphin.layout.fab.ThreeFABMenu;
import com.projectdolphin.layout.view.ListItemRecycleAdapter;

import java.util.List;

public class ClassViewActivity extends AppCompatActivity {

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
        fabMenu.setDeleteFABOnClickListener(getDeleteFABOnClickListener());

        List<com.projectdolphin.data.model.Class> classes = DBAccessHelper.getInstance(getApplicationContext()).getAllClasses();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.view_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter recycleAdapter = new ListItemRecycleAdapter(classes, getCardOnClickListener(), this, new Intent(this, predictDesiredGrade.class));
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
                Intent intent = new Intent(ClassViewActivity.this, SelectItemToModifyActivity.class);
                intent.putExtra(SelectItemToModifyActivity.SELECT_ITEM_TO_MODIFY_DATA_LEVEL_INTENT_KEY, SelectItemToModifyActivity.DataLevel.CLASS.toString());
                intent.putExtra(SelectItemToModifyActivity.SELECT_ITEM_TO_MODIFY_ACTION_MODE_INTENT_KEY, SelectItemToModifyActivity.ActionMode.EDIT.toString());
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener getDeleteFABOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassViewActivity.this, SelectItemToModifyActivity.class);
                intent.putExtra(SelectItemToModifyActivity.SELECT_ITEM_TO_MODIFY_DATA_LEVEL_INTENT_KEY, SelectItemToModifyActivity.DataLevel.CLASS.toString());
                intent.putExtra(SelectItemToModifyActivity.SELECT_ITEM_TO_MODIFY_ACTION_MODE_INTENT_KEY, SelectItemToModifyActivity.ActionMode.DELETE.toString());
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
                intent.putExtra(DBAccessHelper.CLASS_DB_ID_INTENT_KEY, db_id);
                startActivity(intent);
            }
        };
    }
}
