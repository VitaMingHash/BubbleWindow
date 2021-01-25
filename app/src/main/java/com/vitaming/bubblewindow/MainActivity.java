package com.vitaming.bubblewindow;

import androidx.activity.ComponentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vitaming.bubblewindow.itemview.TextItemView;


public class MainActivity extends ComponentActivity {
    private TextView btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);

        BubbleWindow bubbleWindow = new BubbleWindow.Builder(MainActivity.this)
                .setItemView(new TextItemView(MainActivity.this, "tag", "1123"))
                .setItemView(new TextItemView(MainActivity.this, "xf", "3333"))
                .setDirection(Direction.TOP)
                .setOnItemClick(new OnItemClick() {
                    @Override
                    public void onClick(String tag, int position, BubbleWindow bubbleWindow) {

                    }
                })
                .setSelector(false)
                .setTheme(Theme.DARK)
                .setPadding(0)
                .setMargining(0)
                .build();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bubbleWindow.show(btn);
            }
        });
    }
}