package ru.softbalance.newyear.view.fragments;

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

@EFragment(R.layout.fragment_houses)
public class HousesFragment extends BaseFragment implements Savable {

    private static final String STATE_CHECKED_ITEM_IDS = "state_checked_item_ids";

    @ViewById(R.id.next_btn)
    View nextButton;

    private List<CompoundButton> compoundButtons = new ArrayList<>();

    public interface Callback {
        void onGiftsDelivered(HousesFragment f);
    }

    public static HousesFragment newInstance() {
        return HousesFragment_.builder().build();
    }

    @AfterViews
    void init() {
        initCheckboxes();
        checkAllGiftsDelivered();
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

        prepareCheckboxes(view, checkedItemIds);
    }

    private void prepareCheckboxes(ViewGroup viewGroup, List<Integer> checkedItemIds) {
        for(int i=0; i < viewGroup.getChildCount(); i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView instanceof CompoundButton) {
                boolean isChecked = checkedItemIds.contains(childView.getId());
                CompoundButton compoundButton = (CompoundButton) childView;
                compoundButton.setChecked(isChecked);
                compoundButton.setOnCheckedChangeListener(this::onCheckedChange);
                compoundButtons.add(compoundButton);
            } else if (childView instanceof ViewGroup) {
                prepareCheckboxes((ViewGroup) childView, checkedItemIds);
            }
        }
    }

    private void onCheckedChange(CompoundButton compoundButton, boolean isChecked) {
        checkAllGiftsDelivered();
    }

    private void checkAllGiftsDelivered() {
        boolean isAllGiftsDelivered = Stream.of(compoundButtons).allMatch(CompoundButton::isChecked);

        if (isAllGiftsDelivered) {
            nextButton.setVisibility(View.VISIBLE);
        } else {
            nextButton.setVisibility(View.INVISIBLE);
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
            ((Callback) parent).onGiftsDelivered(this);
        }
    }

    @Click(R.id.house_bad)
    void onClickBadHouse() {
        Toast toast = Toast.makeText(getActivity(), R.string.no_gift_for_bad_house, Toast.LENGTH_SHORT);
        toast.getView().setBackgroundResource(R.color.pink_dark);
        toast.show();
    }
}
