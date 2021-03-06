package com.example.trainingzonev4.controllers.baseControllers;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.ControllerChangeType;
import com.example.trainingzonev4.util.WatcherApplication;

public abstract class RefWatchingController extends ButterKnifeController {

    protected RefWatchingController() { }
    protected RefWatchingController(Bundle args) {
        super(args);
    }

    private boolean hasExited;

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (hasExited) {
            WatcherApplication.refWatcher.watch(this);
        }
    }

    @Override
    protected void onChangeEnded(@NonNull ControllerChangeHandler changeHandler, @NonNull ControllerChangeType changeType) {
        super.onChangeEnded(changeHandler, changeType);

        hasExited = !changeType.isEnter;
        if (isDestroyed()) {
            WatcherApplication.refWatcher.watch(this);
        }
    }
}
