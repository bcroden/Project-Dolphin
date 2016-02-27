package com.projectdolphin.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_fab);

        Animation animation;
        if(isFABMenuOut) {
            //retract menu
            animation = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_exit);
            fab.setVisibility(View.INVISIBLE);
            isFABMenuOut = false;
        } else {
            //extend menu
            animation = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_reveal);
            fab.setVisibility(View.VISIBLE);
            isFABMenuOut = true;
        }

        fab.startAnimation(animation);
    }

    public void optionFabClicked(View view) {
        Snackbar.make(view, "Option FAB Clicked", Snackbar.LENGTH_SHORT).show();
    }

    private boolean isFABMenuOut;
}
