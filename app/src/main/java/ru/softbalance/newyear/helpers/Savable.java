package ru.softbalance.newyear.helpers;

import android.os.Bundle;
import android.support.annotation.Nullable;

public interface Savable {
    @Nullable
    Bundle getBundleSaveState();
}
