package ru.softbalance.newyear.view.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.EActivity;

import ru.softbalance.newyear.R;
import ru.softbalance.newyear.view.fragments.StartFragment;

@EActivity
public class MainActivity extends AppCompatActivity implements StartFragment.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            initStartFragment();
        }
    }

    private void initStartFragment() {
        replaceMainFragment(buildStartFragment());
    }

    private StartFragment buildStartFragment() {
        return StartFragment.newInstance();
    }

    private void replaceMainFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                        android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void replaceMainFragment(Fragment fragment, String backStackName) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                        android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(backStackName)
                .commit();
    }

    @Override
    public void onStartClick(StartFragment f) {
        replaceMainFragment(buildStartFragment(), null);
    }
}
