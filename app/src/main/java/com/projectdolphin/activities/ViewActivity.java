package com.projectdolphin.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.projectdolphin.R;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void mainFabClicked(View view) {

        Animation animation = null;

        if(isFABMenuOut) {
            animation = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_exit);
            isFABMenuOut = false;
        } else {
            animation = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_reveal);
            isFABMenuOut = true;
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.upper_fab);
        fab.startAnimation(animation);
    }

    public void optionFabClicked(View view) {
        Snackbar.make(view, "Option FAB Clicked", Snackbar.LENGTH_SHORT).show();
    }

    private boolean isFABMenuOut;
}
