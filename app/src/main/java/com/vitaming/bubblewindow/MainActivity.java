package com.vitaming.bubblewindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vitaming.bubblewindow.itemview.TextItemView;


public class MainActivity extends FragmentActivity {
    private RelativeLayout rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        TextView btn = findViewById(R.id.btn);
        rl = findViewById(R.id.rl);

        BubbleWindow bubbleWindow = new BubbleWindow.Builder(MainActivity.this)
                .setItemView(new TextItemView(MainActivity.this, "tag", "1123"))
//                .setItemVieaw(new TextItemView(MainActivity.this, "xf", "3333"))
                .setDirection(Direction.BOTTOM)
                .setPadding(0)
                .setMargining(0)
                .build();

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bubbleWindow.show(btn);
//            }
//        });
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bubbleWindow.show(rl);
            }
        });
    }
}