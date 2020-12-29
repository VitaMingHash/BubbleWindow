package com.vitaming.bubblewindow;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Ming
 * 12/29/20
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({Theme.LIGHT, Theme.DARK})
public @interface Theme {
    int LIGHT = 0;
    int DARK = 1;
}