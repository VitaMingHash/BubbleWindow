package com.vitaming.bubblewindow.utils;

import android.util.LruCache;
import android.view.View;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Ming
 * 2020-07-21
 * 按键防抖工具类
 */
public class AntiShakeUtil {
    private static LruCache<Integer, Long> clickCache = new LruCache<>(5);

    /**
     * 按键防抖(通过监听设置)
     *
     * @param view            防抖的view
     * @param onClickListener 点击监听
     */
    public static void setAntiShake(@NonNull View view, @Nullable OnClickListener onClickListener) {
        antiShakeInternal(view, onClickListener, Type.DEFAULT, 0);
    }

    /**
     * 按键防抖(通过监听设置)
     *
     * @param view            防抖的view
     * @param clickGapTime    间隔时间
     * @param onClickListener 点击监听
     */
    public static void setAntiShake(@NonNull View view, @NonNull long clickGapTime, @Nullable OnClickListener onClickListener) {
        antiShakeInternal(view, onClickListener, Type.CUSTOMIZE, clickGapTime);
    }

    /**
     * 按键防抖传入view判断是否可点击
     *
     * @param view 防抖的view
     * @return true为可点击 false为不可点击
     */
    public static boolean getAntiShakeStateByView(View view) {
        return getAntiShakeStateInternal(view.getId(), Type.DEFAULT, 0);
    }

    /**
     * 按键防抖传入view判断是否可点击
     *
     * @param view         防抖的view
     * @param clickGapTime 间隔时间
     * @return true为可点击 false为不可点击
     */
    public static boolean getAntiShakeStateByView(View view, long clickGapTime) {
        return getAntiShakeStateInternal(view.getId(), Type.CUSTOMIZE, clickGapTime);
    }

    /**
     * 按键防抖传入viewId判断是否可点击
     *
     * @param viewId 防抖的view的Id
     * @return true为可点击 false为不可点击
     */
    public static boolean getAntiShakeStateById(int viewId) {
        return getAntiShakeStateInternal(viewId, Type.DEFAULT, 0);
    }

    /**
     * 按键防抖传入viewId判断是否可点击
     *
     * @param viewId       防抖的view的Id
     * @param clickGapTime 间隔时间
     * @return true为可点击 false为不可点击
     */
    public static boolean getAntiShakeStateById(int viewId, long clickGapTime) {
        return getAntiShakeStateInternal(viewId, Type.CUSTOMIZE, clickGapTime);
    }

    private static boolean getAntiShakeStateInternal(int viewId, @Type int type, long clickGapTime) {
        long gapTime = 500;
        if (type == Type.CUSTOMIZE) {
            gapTime = clickGapTime;
        }
        if (clickCache.get(viewId) == null) {
            clickCache.put(viewId, System.currentTimeMillis());
            return true;
        } else {
            if ((System.currentTimeMillis() - clickCache.get(viewId) > gapTime)) {
                clickCache.put(viewId, System.currentTimeMillis());
                return true;
            } else {
                return false;
            }
        }
    }

    private static void antiShakeInternal(View view, OnClickListener onClickListener, @Type int type, long clickGapTime) {
        long gapTime = 500;
        if (type == Type.CUSTOMIZE) {
            gapTime = clickGapTime;
        }
        final long[] lastClickTimeStamp = {0};
        if (onClickListener != null) {
            long finalGapTime = gapTime;
            view.setOnClickListener(v -> {
                if ((System.currentTimeMillis() - lastClickTimeStamp[0]) > finalGapTime) {
                    lastClickTimeStamp[0] = System.currentTimeMillis();
                    onClickListener.onClick();
                }
            });
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Type.DEFAULT, Type.CUSTOMIZE})
    private @interface Type {
        int DEFAULT = 0;
        int CUSTOMIZE = 1;
    }

    public interface OnClickListener {
        void onClick();
    }
}