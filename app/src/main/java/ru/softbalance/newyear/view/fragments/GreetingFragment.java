package ru.softbalance.newyear.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.softbalance.newyear.R;
import ru.softbalance.newyear.helpers.Savable;

@EFragment(R.layout.fragment_greeting)
public class GreetingFragment extends BaseFragment {

    @ViewById(R.id.next_btn)
    View nextButton;

    public static GreetingFragment newInstance() {
        return GreetingFragment_.builder().build();
    }

    @Click(R.id.share_btn)
    void onClickNext() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=ru.softbalance.newyear");

        startActivity(Intent.createChooser(share, "Share link!"));
    }
}
