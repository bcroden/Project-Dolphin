package com.projectdolphin.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.projectdolphin.R;
import com.projectdolphin.layout.ThreeFABMenu;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabMenu = new ThreeFABMenu();
        fabMenu.addFABMenuToLayout(this, (ViewGroup) findViewById(R.id.home_layout));
    }



    private ThreeFABMenu fabMenu;
}
