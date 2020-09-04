package com.xylitolz.androidbannerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }
}
