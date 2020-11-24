package com.vitaming.bubblewindow;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Ming
 * 2020-08-14
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({Direction.TOP, Direction.BOTTOM})
public @interface Direction {
    int TOP = 0;
    int BOTTOM = 1;
}
