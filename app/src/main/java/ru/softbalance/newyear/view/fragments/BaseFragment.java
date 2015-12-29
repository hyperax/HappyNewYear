package ru.softbalance.newyear.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import ru.softbalance.newyear.helpers.Savable;

public class BaseFragment extends Fragment {

    private static final String SAVE_STATE = "save_state";

    protected Object getHostParent() {
        Object hostParent = null;
        if (getParentFragment() != null) {
            hostParent = getParentFragment();
        } else if (getActivity() != null) {
            hostParent = getActivity();
        }
        return hostParent;
    }

    @Override
    public void onPause() {
        super.onPause();
        saveState();
    }

    private void saveState() {
        if (this instanceof Savable) {
            getArguments().putBundle(SAVE_STATE, ((Savable) this).getBundleSaveState());
        }
    }

    @Nullable
    protected Bundle restoreSavedState() {
        return getArguments().getBundle(SAVE_STATE);
    }

}
