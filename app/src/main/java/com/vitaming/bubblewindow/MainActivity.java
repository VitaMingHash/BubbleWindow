package com.vitaming.bubblewindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vitaming.bubblewindow.itemview.TextItemView;
import com.vitaming.bubblewindow.utils.ScreenUtil;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        TextView btn = findViewById(R.id.btn);
        BubbleWindow bubbleWindow = new BubbleWindow.Builder(MainActivity.this)
                .setItemView(new TextItemView(MainActivity.this, "tag", "1123"))
//                .setItemVieaw(new TextItemView(MainActivity.this, "xf", "3333"))
                .setDirection(Direction.BOTTOM)
                .setMargining(0)
                .build();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "1231232", Toast.LENGTH_LONG).show();
                bubbleWindow.show(btn);
            }
        });
    }
}