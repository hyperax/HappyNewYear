package ru.softbalance.newyear.helpers;

import android.app.Activity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.plattysoft.leonids.ParticleSystem;
import com.plattysoft.leonids.modifiers.ScaleModifier;

import ru.softbalance.newyear.R;

public class AnimationUtils {

    private static Integer[] effectImagesRes = {R.drawable.star_blue,
            R.drawable.star_green,
            R.drawable.star_pink,
            R.drawable.star_pink};

    private static Integer[] particlesCounts = {10, 15, 25, 50, 75, 100, 125, 150};

    private static Integer[] lifeTimes = {500, 600, 700, 800, 900, 1000, 1100};

    private AnimationUtils() {
    }

    public static void showStarEffectRandom(Activity activity, View view) {

        int imageRes = DataUtils.getRandom(effectImagesRes, R.drawable.star_yellow);
        int maxParticles = DataUtils.getRandom(particlesCounts, 100);
        int lifeTime = DataUtils.getRandom(lifeTimes, 800);

        ParticleSystem ps = new ParticleSystem(activity, maxParticles, imageRes, lifeTime);

        ps.setScaleRange(0.7f, 1.3f);
        ps.setSpeedRange(0.1f, 0.25f);
        ps.setAcceleration(0.0001f, 90);
        ps.setRotationSpeedRange(90, 180);
        ps.setFadeOut(200, new AccelerateInterpolator());
        ps.oneShot(view, maxParticles);
    }

    public static void showLightStarEffect(Activity activity, View view) {

        new ParticleSystem(activity, 10, R.drawable.star, 3000)
                .setSpeedByComponentsRange(-0.1f, 0.1f, -0.1f, 0.02f)
                .setAcceleration(0.000003f, 90)
                .setInitialRotationRange(0, 360)
                .setRotationSpeed(120)
                .setFadeOut(2000)
                .addModifier(new ScaleModifier(0f, 1.5f, 0, 1500))
                .oneShot(view, 10);
    }
}
