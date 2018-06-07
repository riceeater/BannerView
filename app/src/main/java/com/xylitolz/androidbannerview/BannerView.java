package com.xylitolz.androidbannerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 小米Xylitol
 * @email xiaomi987@hotmail.com
 * @desc 继承RelativeLayout实现的可以轮播的无限滚动Banner组件
 * @date 2018-06-06 10:47
 */
public class BannerView extends RelativeLayout {

    private Context context;
    private ViewPager viewPager;//Banner容器
    private BannerAdapter adapter;//适配器
    private List<String> data = new ArrayList<>();

    public BannerView(Context context) {
        this(context,null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    /**
     * 初始化BannerView
     */
    private void init() {
        viewPager = new ViewPager(context);
        viewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(viewPager);

        adapter = new BannerAdapter(context);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
                    viewPager.setCurrentItem(data.size() - 2,false);
                }
                if(position == data.size() - 1) {
                    viewPager.setCurrentItem(1,false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setPageTransformer(true ,new ZoomInTransform());
    }

    public void setData(List<String> data) {
        if(data != null) {
            this.data.clear();
            this.data.add(data.get(data.size() - 1));
            this.data.addAll(data);
            this.data.add(data.get(0));
        }
        adapter.setData(this.data);
        viewPager.setCurrentItem(1);
    }

    public static class BannerAdapter extends PagerAdapter {

        private List<String> data;
        private boolean notify = false;
        private BannerViewHolder holder;

        public BannerAdapter(Context context) {
            holder = new BannerViewHolder(context);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = getView(container,position);
            container.addView(view);
            return view;
        }

        private View getView(ViewGroup container,int position) {
            View view = holder.createView();
            holder.bind(position,data.get(position));
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        public void setData(List<String> data) {
            this.data = data;
            this.notify = true;
            notifyDataSetChanged();
            this.notify = false;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            if(notify) {
                return POSITION_NONE;
            } else {
                return super.getItemPosition(object);
            }
        }
    }

    public interface OnPageClickListener {
        void onPageClick(int position);
    }
}
