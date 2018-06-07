package com.xylitolz.androidbannerview;

import android.view.View;

/**
 * @author 小米Xylitol
 * @email xiaomi987@hotmail.com
 * @desc ViewPager视图生成工具
 * @date 2018-06-07 16:26
 */
public interface BaseBannerViewHolder<T> {
    View createView();
    void bind(int position,T bean);
}
