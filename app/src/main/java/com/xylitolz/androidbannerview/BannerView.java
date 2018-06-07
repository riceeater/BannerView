package com.xylitolz.androidbannerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
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
public class BannerView<T> extends RelativeLayout {

    private Context context;
    private ViewPager viewPager;//Banner容器
    private BannerAdapter adapter;//适配器
    private List<T> data = new ArrayList<>();
    private boolean isInfinity;//是否开启无限模式
    private boolean isLoop;//是否开启自动播放
    private boolean isNesting;//是否显示左右两侧View
    private boolean isTouch = false;//是否触摸
    private PagerIndicator indicator;//指示器
    private Handler handler = new Handler();

    private Runnable loopRunnable = new Runnable() {
        @Override
        public void run() {
            if(isLoop && !isTouch) {
                int currentItem = viewPager.getCurrentItem();
                currentItem++;
                viewPager.setCurrentItem(currentItem,true);
                startScroll();
            }
        }
    };
    private long delayMillis;

    public BannerView(Context context) {
        this(context,null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        readAttrs(context,attrs);
        init();
        initIndicator();
    }

    private void readAttrs(Context context,AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.BannerView);
        isInfinity = typedArray.getBoolean(R.styleable.BannerView_infinity,false);
        isLoop = typedArray.getBoolean(R.styleable.BannerView_loop,false);
        isNesting = typedArray.getBoolean(R.styleable.BannerView_nest,true);
        delayMillis = typedArray.getInteger(R.styleable.BannerView_delayMillis,3000);
        typedArray.recycle();
    }

    private void initIndicator() {
        if(indicator != null) {
            indicator.attachView(this);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    indicator.setCurrentItem(position % adapter.getRealCount());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    public void setIndicator(PagerIndicator indicator) {
        this.indicator = indicator;
        initIndicator();
    }

    /**
     * 初始化BannerView
     */
    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        viewPager = new ViewPager(context);
        viewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(viewPager);

        adapter = new BannerAdapter(context, isInfinity);
        viewPager.setAdapter(adapter);

        viewPager.setPageTransformer(true ,new CoverModeTransformer(viewPager));

        viewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isTouch = true;
                        stopScroll();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isTouch = false;
                        startScroll();
                        break;
                }
                return false;
            }
        });
        viewPager.setOffscreenPageLimit(3);

        setNesting(isNesting,10);

        startScroll();
    }

    public void setNesting(boolean nesting,int margin) {
        this.isNesting = nesting;
        setClipChildren(isNesting);
        if(!isNesting) {
            MarginLayoutParams params = (MarginLayoutParams) viewPager.getLayoutParams();
            params.leftMargin = dpToPx(margin);
            params.rightMargin = dpToPx(margin);
        }
    }

    public void setPageTransformer(boolean reverseDrawingOrder,
                                   @Nullable ViewPager.PageTransformer transformer) {
        viewPager.setPageTransformer(reverseDrawingOrder ,transformer);
    }

    public void startScroll() {
        if(isLoop) {
            handler.postDelayed(loopRunnable, delayMillis);
        }
    }

    public void stopScroll() {
        handler.removeCallbacksAndMessages(null);
    }

    public void setData(List<T> data, BaseBannerViewHolder<T> bannerViewHolder) {
        this.data = data;
        adapter.setHolder(bannerViewHolder);
        adapter.setData(this.data);
        viewPager.setCurrentItem(adapter.getFirstPosition(),true);
    }

    public static class BannerAdapter<T> extends PagerAdapter {

        private List<T> data;
        private boolean notify = false;
        private BaseBannerViewHolder<T> holder;
        private boolean isInfinity;

        public BannerAdapter(Context context,boolean isInfinity) {
            this.isInfinity = isInfinity;
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
            holder.bind(position % getRealCount(),data.get(position % getRealCount()));
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            if(isInfinity) {
                return Integer.MAX_VALUE;
            } else {
                return getRealCount();
            }
        }

        public int getRealCount() {
            return data == null ? 0 : data.size();
        }

        public int getFirstPosition() {
            if(getRealCount() == 0) {
                throw new IllegalArgumentException();
            }
            if(isInfinity) {
                int center = Integer.MAX_VALUE/2;
                center = center - center % getRealCount();
                return center;
            } else {
                return 0;
            }
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        public void setData(List<T> data) {
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

        public void setHolder(BaseBannerViewHolder<T> holder) {
            this.holder = holder;
        }
    }

    public interface OnPageClickListener {
        void onPageClick(int position);
    }

    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
