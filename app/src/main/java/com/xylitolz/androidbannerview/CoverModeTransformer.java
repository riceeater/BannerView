package com.xylitolz.androidbannerview;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author 小米Xylitol
 * @email xiaomi987@hotmail.com
 * @desc 滑动动画
 * @date 2018-06-07 16:42
 */

public class CoverModeTransformer implements ViewPager.PageTransformer {

    private float reduceX = 0.0f;
    private float itemWidth = 0;

    @Override
    public void transformPage(@NonNull View view, float position) {
        float mScaleMax = 1.0f;
        float mScaleMin = 0.9f;
        if (itemWidth == 0) {
            itemWidth = view.getWidth();
            //由于左右边的缩小而减小的x的大小的一半
            reduceX = (2.0f - mScaleMax - mScaleMin) * itemWidth / 2.0f;
        }
        if (position <= -2.0f) {
            view.setTranslationX(reduceX);
            view.setScaleX(mScaleMin);
            view.setScaleY(mScaleMin);
        } else if (position <= 1.0) {
            float scale = (mScaleMax - mScaleMin) * (1.0f - Math.abs(position));
            float translationX = position * -reduceX;
            view.setTranslationX(translationX);
            view.setScaleX(scale + mScaleMin);
            view.setScaleY(scale + mScaleMin);
        } else {
            view.setScaleX(mScaleMin);
            view.setScaleY(mScaleMin);
            view.setTranslationX(-reduceX);
        }
    }
}


