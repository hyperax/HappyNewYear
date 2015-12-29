package ru.softbalance.newyear.view.fragments;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import ru.softbalance.newyear.R;

@EFragment(R.layout.fragment_start)
public class StartFragment extends BaseFragment {

    public static StartFragment newInstance() {
        return StartFragment_.builder().build();
    }

    public interface Callback {
        void onStartClick(StartFragment f);
    }

    @Click(R.id.start_btn)
    void onStartClick() {
        Object parent = getHostParent();
        if (parent instanceof Callback) {
            ((Callback) parent).onStartClick(this);
        }
    }
}
