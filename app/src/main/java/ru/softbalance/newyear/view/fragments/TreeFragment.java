package ru.softbalance.newyear.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

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

@EFragment(R.layout.fragment_tree)
public class TreeFragment extends BaseFragment implements Savable {

    public interface Callback {
        void onClickLightUpTree(TreeFragment f);
    }

    private static final String STATE_CHECKED_ITEM_IDS = "state_checked_item_ids";

    @ViewById(R.id.tree_layout)
    View treeBackgroundView;

    @ViewById(R.id.tree)
    ImageView treeView;

    @ViewById(R.id.next_btn)
    View nextButton;

    @ViewById(R.id.bottom_text)
    View bottomText;

    private List<CompoundButton> compoundButtons = new ArrayList<>();

    public static TreeFragment newInstance() {
        return TreeFragment_.builder().build();
    }

    @AfterViews
    void init() {
        initCheckboxes();
        tryToLightUpTheTree();
    }

    private void initCheckboxes() {
        ViewGroup view = (ViewGroup) getView();
        if (view == null) {
            return;
        }

        Bundle savedState = restoreSavedState();
        List<Integer> checkedItemIds = null;
        if (savedState != null) {
            checkedItemIds = savedState.getIntegerArrayList(STATE_CHECKED_ITEM_IDS);
        }

        if (checkedItemIds == null) {
            checkedItemIds = Collections.emptyList();
        }

        for(int i=0; i < view.getChildCount(); i++) {
            View childView = view.getChildAt(i);
            if (childView instanceof CompoundButton) {
                boolean isChecked = checkedItemIds.contains(childView.getId());
                CompoundButton compoundButton = (CompoundButton) childView;
                compoundButton.setChecked(isChecked);
                compoundButton.setOnCheckedChangeListener(this::onCheckedChange);
                compoundButtons.add(compoundButton);
            }
        }
    }

    private void onCheckedChange(CompoundButton compoundButton, boolean isChecked) {
        tryToLightUpTheTree();
    }

    private void tryToLightUpTheTree() {
        boolean isTreeReady = Stream.of(compoundButtons).allMatch(CompoundButton::isChecked);

        if (isTreeReady) {
            treeBackgroundView.setBackgroundResource(R.drawable.tree_bg_checked);
            treeView.setImageResource(R.drawable.tree_checked);
            nextButton.setVisibility(View.VISIBLE);
            bottomText.setVisibility(View.INVISIBLE);
        } else {
            treeBackgroundView.setBackgroundResource(R.drawable.tree_bg_default);
            treeView.setImageResource(R.drawable.tree_default);
            nextButton.setVisibility(View.GONE);
            bottomText.setVisibility(View.VISIBLE);
        }
    }

    @Nullable
    @Override
    public Bundle getBundleSaveState() {
        Bundle savedState = new Bundle();
        List<Integer> checkedItems = Stream.of(compoundButtons)
                .filter(CompoundButton::isChecked)
                .map(CompoundButton::getId)
                .collect(Collectors.toList());
        savedState.putIntegerArrayList(STATE_CHECKED_ITEM_IDS, new ArrayList<>(checkedItems));
        return savedState;
    }

    @Click(R.id.next_btn)
    void onClickNext() {
        Object parent = getHostParent();
        if (parent instanceof Callback) {
            ((Callback) parent).onClickLightUpTree(this);
        }
    }

}
