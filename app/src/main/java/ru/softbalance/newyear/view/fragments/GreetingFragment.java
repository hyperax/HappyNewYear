package ru.softbalance.newyear.view.fragments;

import android.content.Intent;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import ru.softbalance.newyear.R;

@EFragment(R.layout.fragment_greeting)
public class GreetingFragment extends BaseFragment {

    public static GreetingFragment newInstance() {
        return GreetingFragment_.builder().build();
    }

    @Click(R.id.share_btn)
    void onClickNext() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            share.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        } else{
            //noinspection deprecation
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        }

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getPackageName());

        startActivity(Intent.createChooser(share, "Share link!"));
    }

    private String getPackageName() {
        return getActivity().getPackageName();
    }
}
