Android可自动滚动的广告图控件
效果图：
![](
image/无限滚动的BannerView.gif)

使用：

```xml
<com.xylitolz.androidbannerview.BannerView
        android:id="@+id/banner_view"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        app:coverWidth="150dp"
        app:delayMillis="2000"
        app:infinity="true"
        app:loop="true" />
```

或者在代码中使用：

```java
BannerView<String> bannerView = findViewById(R.id.banner_view);

final List<String> data = new ArrayList<>();
data.add("Banner0");
data.add("Banner1");
data.add("Banner2");
data.add("Banner3");
data.add("Banner4");
bannerView.setData(data, new BannerViewHolder(this));
bannerView.setIndicator(new CirclePageIndicator(data.size()));
bannerView.setOnPageClickListener(new BannerView.OnPageClickListener() {
    @Override
    public void onPageClick(int position) {
        String toast = "this is page " + data.get(position);
        Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
    }
});
```
配合[文档](https://riceeater.gitee.io/post/28-BannerView)食用，风味更佳~
