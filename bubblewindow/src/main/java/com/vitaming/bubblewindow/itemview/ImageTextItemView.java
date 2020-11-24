package com.vitaming.bubblewindow.itemview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.vitaming.bubblewindow.R;


/**
 * Created by Ming
 * 2020-08-11
 */
public class ImageTextItemView extends BaseItemView {
    private String text;
    private int img;

    public ImageTextItemView(@NonNull Context context, @Nullable String tag, @Nullable String text, @DrawableRes int img) {
        super(context, tag);
        this.text = text;
        this.img = img;
    }

    @Override
    public void setView() {
        View itemView = LayoutInflater.from(context).inflate(R.layout.bubble_item_layout_imgtext, null);
        TextView textView = itemView.findViewById(R.id.list_item_tv);
        ImageView imageView = itemView.findViewById(R.id.list_item_img);
        textView.setText(text);
        imageView.setImageDrawable(ContextCompat.getDrawable(context, img));
        view = itemView;
    }
}
