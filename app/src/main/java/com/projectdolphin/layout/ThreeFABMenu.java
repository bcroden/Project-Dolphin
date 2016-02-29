package com.projectdolphin.layout;

import android.app.Activity;
import android.app.Application;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.projectdolphin.R;

/**
 * Helper class which abstracts the implementation of the a FloatingActionButton menu
 *
 * TODO: Add and animate other
 * TODO: Add methods for users to set custom onClick listeners for the other three FABs
 *
 * @author Alex
 */
public class ThreeFABMenu {
    public ThreeFABMenu(Activity activity, ViewGroup parent) {
        //Inflate the menu
        LayoutInflater inflater = activity.getLayoutInflater();
        inflater.inflate(R.layout.fab_menu, parent, true);

        //load the main FAB and set it onClick listener
        //This will always be the same
        FloatingActionButton mainFAB = (FloatingActionButton) activity.findViewById(R.id.main_fab);
        mainFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation;
                if(isFABMenuOut) {
                    //retract menu

                    Log.i("MY_TAG", "Menu in");
                    animation = addFabExit;
                    addFab.setVisibility(View.INVISIBLE);
                    isFABMenuOut = false;
                } else {
                    //extend menu
                    Log.i("MY_TAG", "Menu out");
                    animation = addFabEnter;
                    addFab.setVisibility(View.VISIBLE);
                    isFABMenuOut = true;
                }

                addFab.startAnimation(animation);
            }
        });

        addFab = (FloatingActionButton) activity.findViewById(R.id.add_fab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Option FAB Clicked", Snackbar.LENGTH_SHORT).show();
            }
        });
        addFabEnter = AnimationUtils.loadAnimation(activity.getApplication(), R.anim.fab_reveal);
        addFabExit = AnimationUtils.loadAnimation(activity.getApplication(), R.anim.fab_exit);
    }

    private boolean isFABMenuOut;
    private FloatingActionButton addFab;
    private Animation addFabEnter, addFabExit;
}
