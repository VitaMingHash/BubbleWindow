package com.vitaming.bubblewindow.itemview;

import android.content.Context;
import android.view.View;


/**
 * Created by Ming
 * 2020-08-11
 */
public abstract class BaseItemView {

    public View view;
    public String tag;
    public Context context;

    public BaseItemView(Context context, String tag){
        this.tag = tag;
        this.context = context;
    }

    public abstract void setView();
}
