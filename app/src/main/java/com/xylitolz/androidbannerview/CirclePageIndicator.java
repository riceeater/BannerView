package com.xylitolz.androidbannerview;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 小米Xylitol
 * @email xiaomi987@hotmail.com
 * @desc 圆点指示器
 * @date 2018-06-07 17:36
 */
public class CirclePageIndicator implements PagerIndicator {

    private int size;
    private List<ImageView> imageViewList = new ArrayList<>();
    private int currentItem = 0;

    public CirclePageIndicator(int size) {
        this.size = size;
    }

    @Override
    public void setCurrentItem(int position) {
        this.currentItem = position;
        for (int i = 0; i < imageViewList.size(); i++) {
            ImageView imageView = imageViewList.get(i);
            if (i == currentItem) {
                imageView.setImageResource(R.drawable.indicator_selected);
            } else {
                imageView.setImageResource(R.drawable.indicator_normal);
            }
        }
    }

    @Override
    public void attachView(ViewGroup viewGroup) {
        LinearLayout linearLayout = new LinearLayout(viewGroup.getContext());
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(viewGroup.getContext());
            if (i == currentItem) {
                imageView.setImageResource(R.drawable.indicator_selected);
            } else {
                imageView.setImageResource(R.drawable.indicator_normal);
            }
            imageViewList.add(imageView);
            linearLayout.addView(imageView);
            if (i != size - 1) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                marginLayoutParams.rightMargin = BannerView.dpToPx(5);
            }
        }
        viewGroup.addView(linearLayout);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        params.height = BannerView.dpToPx(15);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        linearLayout.setBackgroundColor(0x33000000);
    }
}
