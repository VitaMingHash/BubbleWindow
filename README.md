# BubbleWindow气泡弹窗

```
implementation 'com.github.VitaMingHash:BubbleWindow:v1.0.0'
```
创建
```
BubbleWindow bubbleWindow = new BubbleWindow.Builder(MainActivity.this)
                .setItemView(new TextItemView(MainActivity.this, "tag", "1123"))
                .setDirection(Direction.BOTTOM)
                .setMargining(0)
                .build();
```

显示
```
bubbleWindow.show(btn);
```
