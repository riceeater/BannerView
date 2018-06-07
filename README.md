Android可自动滚动的广告图控件
效果图：
![](http://p6z0jdp7l.bkt.clouddn.com/view/BannerView.gif)

使用：

```xml
<com.xylitolz.androidbannerview.BannerView
        android:id="@+id/banner_view"
        android:layout_width="match_parent"
        android:layout_height="120dp"
        app:delayMillis="2000"
        app:loop="true"
        app:infinity="true"
        />
```

或者在代码中使用：

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    bannerView = findViewById(R.id.banner_view);

    final List<String> data = new ArrayList<>();
    data.add("Banner0");
    data.add("Banner1");
    data.add("Banner2");
    bannerView.setData(data,new BannerViewHolder(this));
    bannerView.setNesting(true,20);
    bannerView.setIndicator(new CirclePageIndicator(data.size()));
    bannerView.setOnPageClickListener(new BannerView.OnPageClickListener() {
        @Override
        public void onPageClick(int position) {
            String toast = "this is page " + data.get(position);
            Toast.makeText(MainActivity.this,toast,Toast.LENGTH_SHORT).show();
        }
    });
}
```
配合[文档](http://www.riceeater.info/articles/Android/View/BannerView/)食用，风味更佳~