package com.example.trainingzonev4.controllers.baseControllers;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.bluelinelabs.conductor.Controller;
import com.example.trainingzonev4.util.ActionBarProvider;

public abstract class BaseController extends RefWatchingController {

    protected BaseController() { }

    protected BaseController(Bundle args) {
        super(args);
    }


    protected ActionBar getActionBar() {
        ActionBarProvider actionBarProvider = ((ActionBarProvider)getActivity());
        return actionBarProvider != null ? actionBarProvider.getSupportActionBar() : null;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        setTitle();
        super.onAttach(view);
    }

    protected void setTitle() {
        Controller parentController = getParentController();
        while (parentController != null) {
            if (parentController instanceof BaseController && ((BaseController)parentController).getTitle() != null) {
                return;
            }
            parentController = parentController.getParentController();
        }

        String title = getTitle();
        ActionBar actionBar = getActionBar();
        if (title != null && actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected String getTitle() {
        return null;
    }
}
