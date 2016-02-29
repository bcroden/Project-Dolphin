package com.projectdolphin.layout;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
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
    public ThreeFABMenu(Activity activity,
                        ViewGroup parent,
                        View.OnClickListener addFABListener,
                        View.OnClickListener editFABListener,
                        View.OnClickListener deleteFABListener) {
        //Inflate the menu
        LayoutInflater inflater = activity.getLayoutInflater();
        inflater.inflate(R.layout.fab_menu, parent, true);

        //load the main FAB and set it onClick listener
        //This will always be the same
        FloatingActionButton mainFAB = (FloatingActionButton) activity.findViewById(R.id.main_fab);
        mainFAB.setOnClickListener(getMainFabOnClickListener());

        fabs = new FAB[3];

        FloatingActionButton addFab = (FloatingActionButton) activity.findViewById(R.id.add_fab);
        addFab.setOnClickListener(addFABListener);
        Animation addFabEnter = AnimationUtils.loadAnimation(activity.getApplication(), R.anim.left_fab_reveal);
        Animation addFabExit = AnimationUtils.loadAnimation(activity.getApplication(), R.anim.left_fab_exit);
        fabs[0] = new FAB(addFab, addFabEnter, addFabExit);

        FloatingActionButton editFab = (FloatingActionButton) activity.findViewById(R.id.edit_fab);
        editFab.setOnClickListener(editFABListener);
        Animation editFabEnter = AnimationUtils.loadAnimation(activity.getApplication(), R.anim.left_fab_reveal);
        Animation editFabExit = AnimationUtils.loadAnimation(activity.getApplication(), R.anim.left_fab_exit);
        fabs[1] = new FAB(editFab, editFabEnter, editFabExit);

        FloatingActionButton delFab = (FloatingActionButton) activity.findViewById(R.id.delete_fab);
        delFab.setOnClickListener(deleteFABListener);
        Animation delFabEnter = AnimationUtils.loadAnimation(activity.getApplication(), R.anim.left_fab_reveal);
        Animation delFabExit = AnimationUtils.loadAnimation(activity.getApplication(), R.anim.left_fab_exit);
        fabs[2] = new FAB(delFab, delFabEnter, delFabExit);
    }

    private View.OnClickListener getMainFabOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFABMenuOut) {
                    //retract menu
                    for(FAB fab : fabs) {
                        fab.getFab().setVisibility(View.INVISIBLE);
                        fab.getFab().startAnimation(fab.getExit());
                    }

                    isFABMenuOut = false;
                } else {
                    //extend menu
                    for(FAB fab : fabs) {
                        fab.getFab().setVisibility(View.VISIBLE);
                        fab.getFab().startAnimation(fab.getEnter());
                    }

                    isFABMenuOut = true;
                }
            }
        };
    }

    private boolean isFABMenuOut;
    private FAB[] fabs;

    private class FAB {
        public FAB(FloatingActionButton fab, Animation enter, Animation exit) {
            this.fab = fab;
            this.enter = enter;
            this.exit = exit;
        }

        public FloatingActionButton getFab() {
            return fab;
        }

        public Animation getEnter() {
            return enter;
        }

        public Animation getExit() {
            return exit;
        }

        private FloatingActionButton fab;
        private Animation enter, exit;
    }
}
