package com.projectdolphin.layout;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.projectdolphin.R;

/**
 * Helper class which abstracts the implementation of the a FloatingActionButton menu
 *
 * @author Alex
 */
public class ThreeFABMenu {
    public ThreeFABMenu(Activity activity, ViewGroup parent) {
        this(activity, parent, null, null, null);
    }
    public ThreeFABMenu(Activity activity,
                        ViewGroup parent,
                        View.OnClickListener addFABListener,
                        View.OnClickListener editFABListener,
                        View.OnClickListener deleteFABListener) {
        //Inflate the menu
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //load the main FAB and set it onClick listener
        //This will always be the same
        FloatingActionButton mainFAB = (FloatingActionButton) inflater.inflate(R.layout.three_fab_menu_main_fab, parent, true).findViewById(R.id.main_fab);
        mainFAB.setOnClickListener(getMainFabOnClickListener());

        fabs = new FAB[3];

        FloatingActionButton addFab = (FloatingActionButton) inflater.inflate(R.layout.three_fab_menu_add_fab, parent, true).findViewById(R.id.add_fab);
        if(addFABListener != null)
            addFab.setOnClickListener(addFABListener);
        else
            addFab.setOnClickListener(getDefaultOnClickListener(parent.getContext()));
        Animation addFabEnter = AnimationUtils.loadAnimation(parent.getContext(), R.anim.top_fab_reveal);
        Animation addFabExit = AnimationUtils.loadAnimation(parent.getContext(), R.anim.top_fab_exit);
        fabs[ADD_FAB_INDEX] = new FAB(addFab, addFabEnter, addFabExit);

        FloatingActionButton editFab = (FloatingActionButton) inflater.inflate(R.layout.three_fab_menu_edit_fab, parent, true).findViewById(R.id.edit_fab);
        if(editFABListener != null)
            editFab.setOnClickListener(editFABListener);
        else
            editFab.setOnClickListener(getDefaultOnClickListener(parent.getContext()));
        Animation editFabEnter = AnimationUtils.loadAnimation(parent.getContext(), R.anim.corner_fab_reveal);
        Animation editFabExit = AnimationUtils.loadAnimation(parent.getContext(), R.anim.corner_fab_exit);
        fabs[EDIT_FAB_INDEX] = new FAB(editFab, editFabEnter, editFabExit);

        FloatingActionButton delFab = (FloatingActionButton) inflater.inflate(R.layout.three_fab_menu_delete_fab, parent, true).findViewById(R.id.delete_fab);
        if(deleteFABListener != null)
            delFab.setOnClickListener(deleteFABListener);
        else
            delFab.setOnClickListener(getDefaultOnClickListener(parent.getContext()));
        Animation delFabEnter = AnimationUtils.loadAnimation(parent.getContext(), R.anim.left_fab_reveal);
        Animation delFabExit = AnimationUtils.loadAnimation(parent.getContext(), R.anim.left_fab_exit);
        fabs[DEL_FAB_INDEX] = new FAB(delFab, delFabEnter, delFabExit);

        for(FAB fab : fabs) {
            fab.getEnter().setDuration(ANIMATION_DURATION);
            fab.getExit().setDuration(ANIMATION_DURATION);
        }
    }

    public void setAddFABOnClickListener(View.OnClickListener listener) {
        fabs[ADD_FAB_INDEX].getFab().setOnClickListener(listener);
    }

    public void setEditFABOnClickListener(View.OnClickListener listener) {
        fabs[EDIT_FAB_INDEX].getFab().setOnClickListener(listener);
    }

    public void setDeleteFABOnClickListener(View.OnClickListener listener) {
        fabs[DEL_FAB_INDEX].getFab().setOnClickListener(listener);
    }


    public View.OnClickListener getDefaultOnClickListener(final Context context) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,
                                "OnClickListener is not defined for this FAB",
                                Toast.LENGTH_SHORT)
                                .show();
            }
        };
    }

    private View.OnClickListener getMainFabOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFABMenuOut) {
                    //retract menu
                    for(FAB fab : fabs) {
                        fab.getFab().setVisibility(View.GONE);
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
    private final int ANIMATION_DURATION = 500;
    private final int ADD_FAB_INDEX = 0, EDIT_FAB_INDEX = 1, DEL_FAB_INDEX = 2;
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
