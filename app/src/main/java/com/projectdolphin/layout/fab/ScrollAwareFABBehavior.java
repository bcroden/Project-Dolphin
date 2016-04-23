package com.projectdolphin.layout.fab;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.projectdolphin.R;

/**
 * Causes a Floating Action Button to hide/appear when scrolling
 *
 * @author Alex
 */
public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {
    public ScrollAwareFABBehavior(Context context, AttributeSet attributeSet) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton fab, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, fab, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton fab, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, fab, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if(fab.getVisibility() != View.GONE) {
            if (dyConsumed > 0 && !animationInProgress && fab.getVisibility() == View.VISIBLE) {
                Animation animation = AnimationUtils.loadAnimation(coordinatorLayout.getContext(), R.anim.top_fab_exit);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        ScrollAwareFABBehavior.this.animationInProgress = true;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ScrollAwareFABBehavior.this.animationInProgress = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                if(fab.getId() == R.id.main_fab)
                    fab.setVisibility(View.INVISIBLE);
                else
                    fab.setVisibility(View.GONE);
                fab.startAnimation(animation);
                //scrolling down so hide the fab
            } else if (dyConsumed < 0 && fab.getVisibility() != View.VISIBLE) {
                fab.show(); //scrolling up so hide the fab
            }
        }
    }

    public boolean animationInProgress = false;
}
