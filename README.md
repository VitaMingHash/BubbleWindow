# BubbleWindow气泡弹窗

```
implementation 'com.github.VitaMingHash:BubbleWindow:v1.0.0'
```
```
BubbleWindow bubbleWindow = new BubbleWindow.Builder(MainActivity.this)
                .setItemView(new TextItemView(MainActivity.this, "tag", "1123"))
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
```
