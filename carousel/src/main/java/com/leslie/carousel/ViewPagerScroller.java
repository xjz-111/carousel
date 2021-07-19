package com.leslie.carousel;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

/**
 * 滑动控制器
 *
 * 作者：xjzhao
 * 时间：2021-07-19 10:34
 */
class ViewPagerScroller extends Scroller {
    public static int DEFAULT_SCROLL_DURATION = 1500;
    // 滑动速度默认值
    private int scrollDuration = DEFAULT_SCROLL_DURATION;

    public ViewPagerScroller(Context context) {
        super(context);
    }

    public ViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    public ViewPagerScroller(Context context, int scrollDuration) {
        super(context);
        if (scrollDuration > 0) {
            this.scrollDuration = scrollDuration;
        }
    }

    public void setScrollDuration(int ms) {
        this.scrollDuration = ms;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, scrollDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, scrollDuration);
    }

    public void initViewPagerScroll(ViewPager viewPager) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(viewPager, this);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
