package ru.softbalance.newyear.view.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;

import com.plattysoft.leonids.ParticleSystem;

import org.androidannotations.annotations.EActivity;

import ru.softbalance.newyear.R;
import ru.softbalance.newyear.view.fragments.GreetingFragment;
import ru.softbalance.newyear.view.fragments.HousesFragment;
import ru.softbalance.newyear.view.fragments.StartFragment;
import ru.softbalance.newyear.view.fragments.TreeFragment;

@EActivity
public class MainActivity extends AppCompatActivity implements StartFragment.Callback,
        TreeFragment.Callback,
        HousesFragment.Callback{

    private ParticleSystem ps;

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
        replaceMainFragment(buildTreeFragment(), null);
    }

    private TreeFragment buildTreeFragment() {
        return TreeFragment.newInstance();
    }

    @Override
    public void onClickLightUpTree(TreeFragment f) {
        replaceMainFragment(buildHousesFragment(), null);
    }

    private HousesFragment buildHousesFragment() {
        return HousesFragment.newInstance();
    }

    @Override
    public void onGiftsDelivered(HousesFragment f) {
        replaceMainFragment(buildGreetingFragment(), null);
    }

    private Fragment buildGreetingFragment() {
        return GreetingFragment.newInstance();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Create a particle system and start emiting
                ps = new ParticleSystem(this, 100, R.drawable.star_pink, 800);
                ps.setScaleRange(0.7f, 1.3f);
                ps.setSpeedRange(0.05f, 0.1f);
                ps.setRotationSpeedRange(90, 180);
                ps.setFadeOut(200, new AccelerateInterpolator());
                ps.emit((int) event.getX(), (int) event.getY(), 40);
                break;
            case MotionEvent.ACTION_MOVE:
                ps.updateEmitPoint((int) event.getX(), (int) event.getY());
                break;
            case MotionEvent.ACTION_UP:
                ps.stopEmitting();
                break;
        }
        return true;
    }
}
