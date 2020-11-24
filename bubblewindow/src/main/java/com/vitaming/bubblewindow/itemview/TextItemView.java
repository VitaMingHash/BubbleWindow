package com.vitaming.bubblewindow.itemview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.vitaming.bubblewindow.R;


/**
 * Created by Ming
 * 2020-08-11
 */
public class TextItemView extends BaseItemView {
    private String text;
    public TextItemView(Context context, String tag, String text) {
        super(context, tag);
        this.text = text;
    }

    @Override
    public void setView() {
        View itemView = LayoutInflater.from(context).inflate(R.layout.bubble_item_layout_text, null);
        TextView textView = itemView.findViewById(R.id.list_item_tv);
        textView.setText(text);
        view = itemView;
    }
}
