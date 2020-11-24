package com.vitaming.bubblewindow;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.vitaming.bubblewindow.itemview.BaseItemView;
import com.vitaming.bubblewindow.utils.AntiShakeUtil;
import com.vitaming.bubblewindow.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming
 * 2020-08-11
 * 气泡弹窗
 * todo 判断点击范围 出范围抬起时没有点击效果
 */
public class BubbleWindow {
    private List<BaseItemView> baseItemViewList = new ArrayList<>();
    private OnItemClick onItemClick;

    /**
     * 弹窗宽度
     */
    private static int dialogWidth = 150;

    private AlertDialog dialog;
    private Activity context;
    private View angleView;

    /**
     * 弹窗方向 默认向下
     */
    private int direction = Direction.TOP;

    /**
     * 弹窗距离屏幕边距
     */
    private int padding = 20;

    /**
     * 弹出的view距离
     */
    private int marging = 0;

    public static class Builder {
        private BubbleWindow bubbleWindow;

        public Builder(Activity context) {
            bubbleWindow = new BubbleWindow();
            bubbleWindow.context = context;
        }

        public Builder setPadding(int padding) {
            bubbleWindow.padding = padding;
            return this;
        }

        public Builder setMargining(int marging) {
            bubbleWindow.marging = marging;
            return this;
        }

        public Builder setDirection(@Direction int direction) {
            bubbleWindow.direction = direction;
            return this;
        }

        public Builder setItemView(BaseItemView itemView) {
            itemView.setView();
            bubbleWindow.baseItemViewList.add(itemView);
            return this;
        }

        public Builder setOnItemClick(OnItemClick onItemClick) {
            bubbleWindow.onItemClick = onItemClick;
            return this;
        }

        public BubbleWindow build() {
            bubbleWindow.setBubbleWindow();
            return bubbleWindow;
        }
    }

    private void setBubbleWindow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.bubble_dialog_style);
        dialog = builder.create();
        View dialogView = View.inflate(context, R.layout.bubble_layout, null);
        if (direction == Direction.BOTTOM) {
            angleView = dialogView.findViewById(R.id.bubble_angle_top);
            dialogView.findViewById(R.id.bottom_rl).setVisibility(View.GONE);
        } else if (direction == Direction.TOP) {
            angleView = dialogView.findViewById(R.id.bubble_angle_bottom);
            dialogView.findViewById(R.id.top_rl).setVisibility(View.GONE);
        }

        LinearLayout linearLayout = dialogView.findViewById(R.id.bubble_layout);
        if (baseItemViewList.size() > 0) {
            //设置item的selector
            switch (baseItemViewList.size()) {
                case 1:
                    baseItemViewList.get(0).view.setBackground(ContextCompat.getDrawable(context, R.drawable.bubblewindow_one_selector));
                    break;
                default:
                    baseItemViewList.get(0).view.setBackground(ContextCompat.getDrawable(context, R.drawable.bubblewindow_top_selector));
                    baseItemViewList.get(baseItemViewList.size() - 1).view.setBackground(ContextCompat.getDrawable(context, R.drawable.bubblewindow_bottom_selector));
                    if (baseItemViewList.size() > 2) {
                        for (int i = 1; i < baseItemViewList.size() - 1; i++) {
                            baseItemViewList.get(i).view.setBackground(ContextCompat.getDrawable(context, R.drawable.bubblewindow_center_selector));
                        }
                    }
                    break;
            }
            //设置角的selector
            if (direction == Direction.BOTTOM) {
                baseItemViewList.get(0).view.setOnTouchListener((v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            angleView.setPressed(true);
                            baseItemViewList.get(0).view.setPressed(true);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (!isTouchPointInView(baseItemViewList.get(0).view, event.getRawX(), event.getRawY())) {
                                angleView.setPressed(false);
                                baseItemViewList.get(0).view.setPressed(false);
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            if (baseItemViewList.get(0).view.isPressed()) {
                                angleView.setPressed(false);
                                baseItemViewList.get(0).view.setPressed(false);
                                if (onItemClick != null) {
                                    onItemClick.onClick(baseItemViewList.get(0).tag, 0, BubbleWindow.this);
                                }
                            }
                            break;
                    }
                    return true;
                });
            }

            if (direction == Direction.TOP) {
                baseItemViewList.get(baseItemViewList.size() - 1).view.setOnTouchListener((v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            angleView.setPressed(true);
                            baseItemViewList.get(baseItemViewList.size() - 1).view.setPressed(true);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (!isTouchPointInView(baseItemViewList.get(baseItemViewList.size() - 1).view, event.getRawX(), event.getRawY())) {
                                angleView.setPressed(false);
                                baseItemViewList.get(baseItemViewList.size() - 1).view.setPressed(false);
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            if (baseItemViewList.get(baseItemViewList.size() - 1).view.isPressed()) {
                                angleView.setPressed(false);
                                baseItemViewList.get(baseItemViewList.size() - 1).view.setPressed(false);
                                if (onItemClick != null) {
                                    onItemClick.onClick(baseItemViewList.get(baseItemViewList.size() - 1).tag, baseItemViewList.size() - 1, BubbleWindow.this);
                                }
                            }
                            break;
                    }
                    return true;
                });
            }

            //设置点击事件以及item
            for (int i = 0; i < baseItemViewList.size(); i++) {
                //设置点击事件
                if (onItemClick != null) {
                    int finalI = i;
                    baseItemViewList.get(i).view.setOnClickListener(v -> {
                        if (AntiShakeUtil.getAntiShakeStateByView(v)) {
                            onItemClick.onClick(baseItemViewList.get(finalI).tag, finalI, BubbleWindow.this);
                        }
                    });
                }
                //添加view
                linearLayout.addView(baseItemViewList.get(i).view, LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(context, 50));
                if (i != baseItemViewList.size() - 1) {
                    linearLayout.addView(lineView(), LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(context, 0.5f));
                }

            }
        }
        dialog.setView(dialogView);
    }

    private View lineView() {
        View view = new View(context);
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.color_DDDDDD));
        return view;
    }

    //设置dialog位置
    private void setPosition(View view) {
        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP | Gravity.LEFT);
        WindowManager.LayoutParams lp = window.getAttributes();
        if (direction == Direction.TOP) {
            int commonX = (int) (view.getX() + view.getWidth() / 2 - ScreenUtil.dp2px(context, dialogWidth / 2));
            int commonY;
            if (context instanceof AppCompatActivity && ((AppCompatActivity) context).getSupportActionBar().isShowing()) {
                commonY = (int) view.getY() + ScreenUtil.getActionBarHeight(context) - getDialogHeight() - ScreenUtil.dp2px(context, marging);
            } else {
                commonY = (int) view.getY() - getDialogHeight() - ScreenUtil.dp2px(context, marging);
            }
            //X方向限制
            if (commonX < ScreenUtil.dp2px(context, padding)) {
                lp.x = ScreenUtil.dp2px(context, padding);
            } else if ((int) (view.getX() + ScreenUtil.dp2px(context, dialogWidth)) > ScreenUtil.getScreenWidth(context)) {
                lp.x = ScreenUtil.getScreenWidth(context) - ScreenUtil.dp2px(context, padding) - ScreenUtil.dp2px(context, dialogWidth);
            } else {
                lp.x = commonX;
            }
            //Y方向限制
            if (commonY < ScreenUtil.dp2px(context, padding)) {
                lp.y = ScreenUtil.dp2px(context, padding);
            } else {
                lp.y = commonY;
            }
            //移动角的位置
            float commonMove = view.getX() + view.getWidth() / 2 - (lp.x + ScreenUtil.dp2px(context, dialogWidth / 2));
            if (commonMove > ScreenUtil.dp2px(context, dialogWidth / 2) - ScreenUtil.dp2px(context, 20)) {
                angleView.setTranslationX(ScreenUtil.dp2px(context, dialogWidth / 2) - ScreenUtil.dp2px(context, 20));
            } else if (commonMove < -(ScreenUtil.dp2px(context, dialogWidth / 2) - ScreenUtil.dp2px(context, 10))) {
                angleView.setTranslationX(-(ScreenUtil.dp2px(context, dialogWidth / 2) - ScreenUtil.dp2px(context, 20)));
            } else {
                angleView.setTranslationX(commonMove);
            }
        } else if (direction == Direction.BOTTOM) {
            int commonX = (int) (view.getX() + view.getWidth() / 2 - ScreenUtil.dp2px(context, dialogWidth / 2));
            int commonY;
            if (context instanceof AppCompatActivity && ((AppCompatActivity) context).getSupportActionBar().isShowing()) {
                commonY = (int) view.getY() + ScreenUtil.getActionBarHeight(context) + view.getHeight() - ScreenUtil.dp2px(context, marging);
            } else {
                commonY = (int) view.getY() + view.getHeight() - ScreenUtil.dp2px(context, marging);
            }
            //X方向限制
            if (commonX < ScreenUtil.dp2px(context, padding)) {
                lp.x = ScreenUtil.dp2px(context, padding);
            } else if ((int) (view.getX() + ScreenUtil.dp2px(context, dialogWidth)) > ScreenUtil.getScreenWidth(context)) {
                lp.x = ScreenUtil.getScreenWidth(context) - ScreenUtil.dp2px(context, padding) - ScreenUtil.dp2px(context, dialogWidth);
            } else {
                lp.x = commonX;
            }
            //Y方向限制
            if (commonY > ScreenUtil.getScreenHeight(context) - getDialogHeight()) {
                lp.y = ScreenUtil.getScreenHeight(context) - ScreenUtil.dp2px(context, padding) - getDialogHeight();
            } else {
                lp.y = commonY;
            }
            //移动角的位置
            float commonMove = view.getX() + view.getWidth() / 2 - (lp.x + ScreenUtil.dp2px(context, dialogWidth / 2));
            if (commonMove > ScreenUtil.dp2px(context, dialogWidth / 2) - ScreenUtil.dp2px(context, 20)) {
                angleView.setTranslationX(ScreenUtil.dp2px(context, dialogWidth / 2) - ScreenUtil.dp2px(context, 20));
            } else if (commonMove < -(ScreenUtil.dp2px(context, dialogWidth / 2) - ScreenUtil.dp2px(context, 10))) {
                angleView.setTranslationX(-(ScreenUtil.dp2px(context, dialogWidth / 2) - ScreenUtil.dp2px(context, 20)));
            } else {
                angleView.setTranslationX(commonMove);
            }
        }

        window.setAttributes(lp);
        dialog.show();
    }

    /**
     * 弹窗的高度
     *
     * @return
     */
    private int getDialogHeight() {
        return ScreenUtil.dp2px(context, (float) (50 * baseItemViewList.size() + 0.5 * (baseItemViewList.size() - 1) + 16 + marging));
    }

    /**
     * 判断是否点击到view内
     *
     * @param view 需要判断的view
     * @param x    点击的x坐标  event.getRawX()
     * @param y    点击的y坐标  event.getRawY()
     * @return true在view内
     */

    private boolean isTouchPointInView(View view, float x, float y) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return y >= top && y <= bottom && x >= left && x <= right;
    }

    /**
     * 弹窗实现类
     */
    private void setWindowInternal() {

    }

    /**
     * 显示弹窗
     */
    public void show(View view) {
        if (baseItemViewList.size() > 0) {
            setPosition(view);
        }
    }

    /**
     * 隐藏弹窗
     */
    public void dismiss() {
        dialog.cancel();
    }
}
