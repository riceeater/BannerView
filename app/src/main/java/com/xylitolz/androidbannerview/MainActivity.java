package com.xylitolz.androidbannerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BannerView bannerView;

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
}
