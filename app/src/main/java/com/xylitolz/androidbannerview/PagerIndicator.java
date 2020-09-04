package com.xylitolz.androidbannerview;

import android.view.ViewGroup;

/**
 * @author 小米Xylitol
 * @email xiaomi987@hotmail.com
 * @desc 指示器
 * @date 2018-06-07 17:15
 */
public interface PagerIndicator {
    void setCurrentItem(int position);

    void attachView(ViewGroup viewGroup);
}
