package com.example.trainingzonev4.controllers.baseControllers.abstractFabToDialog;

import android.annotation.TargetApi;
import android.os.Build;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bluelinelabs.conductor.changehandler.androidxtransition.TransitionChangeHandler;
import com.example.trainingzonev4.R;
import com.example.trainingzonev4.transform.fabTransform.FabTransform;
import com.example.trainingzonev4.util.AnimUtils;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public abstract class FabAbstractToDialogTransitionChangeHandler extends TransitionChangeHandler {

    private View fab;
    private View dialogBackground;
    private ViewGroup fabParent;

    @Override
    @NonNull
    protected Transition getTransition(@NonNull final ViewGroup container, @Nullable final View from, @Nullable final View to, boolean isPush) {
        Transition backgroundFade = new Fade();
        backgroundFade.addTarget(R.id.dialog_background);

        Transition fabTransform = new FabTransform(ContextCompat.getColor(container.getContext(), R.color.colorAccent), R.drawable.ic_github_face);

        TransitionSet set = new TransitionSet();
        set.addTransition(backgroundFade);
        set.addTransition(fabTransform);

        return set;
    }

    @Override
    public void prepareForTransition(@NonNull ViewGroup container, @Nullable View from, @Nullable View to, @NonNull Transition transition, boolean isPush, @NonNull OnTransitionPreparedListener onTransitionPreparedListener) {
        fab = isPush ? from.findViewById(R.id.fab) : to.findViewById(R.id.fab);
        fabParent = (ViewGroup)fab.getParent();

        if (!isPush) {

            fabParent.removeView(fab);
            fab.setVisibility(View.VISIBLE);


            dialogBackground = from.findViewById(R.id.dialog_background);
            ((ViewGroup)dialogBackground.getParent()).removeView(dialogBackground);
            fabParent.addView(dialogBackground);
        }

        onTransitionPreparedListener.onPrepared();
    }

    @Override
    public void executePropertyChanges(@NonNull ViewGroup container, @Nullable View from, @Nullable View to, @Nullable Transition transition, boolean isPush) {
        if (isPush) {
            fabParent.removeView(fab);
            container.addView(to);


            AnimUtils.TransitionEndListener endListener = new AnimUtils.TransitionEndListener() {
                @Override
                public void onTransitionCompleted(Transition transition) {
                    fab.setVisibility(View.GONE);
                    fabParent.addView(fab);
                    fab = null;
                    fabParent = null;
                }
            };
            if (transition != null) {
                transition.addListener(endListener);
            } else {
                endListener.onTransitionCompleted(null);
            }
        } else {
            dialogBackground.setVisibility(View.INVISIBLE);
            fabParent.addView(fab);
            container.removeView(from);

            AnimUtils.TransitionEndListener endListener = new AnimUtils.TransitionEndListener() {
                @Override
                public void onTransitionCompleted(Transition transition) {
                    fabParent.removeView(dialogBackground);
                    dialogBackground = null;
                }
            };
            if (transition != null) {
                transition.addListener(endListener);
            } else {
                endListener.onTransitionCompleted(null);
            }
        }
    }

}
