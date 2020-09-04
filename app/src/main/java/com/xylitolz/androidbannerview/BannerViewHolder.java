package com.xylitolz.androidbannerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author 小米Xylitol
 * @email xiaomi987@hotmail.com
 * @desc Banner子项帮助类
 * @date 2018-06-06 14:09
 */
public class BannerViewHolder implements BaseBannerViewHolder<String> {

    private Context context;
    private TextView tvBanner;
    private ImageView ivBanner;

    public BannerViewHolder(Context context) {
        this.context = context;
    }

    public View createView(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner, parent, false);
        tvBanner = view.findViewById(R.id.tv_banner);
        ivBanner = view.findViewById(R.id.iv_banner);
        return view;
    }

    public void bind(int position, String s) {
        if (position % 3 == 0) {
            ivBanner.setBackgroundColor(0xffff0000);
        } else if (position % 3 == 1) {
            ivBanner.setBackgroundColor(0xff00ff00);
        } else {
            ivBanner.setBackgroundColor(0xff0000ff);
        }
        tvBanner.setText(s);
    }
}
