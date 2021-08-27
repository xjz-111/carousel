package com.leslie.carousel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * ViewPager
 *
 * 作者：xjzhao
 * 时间：2021-07-19 10:33
 */
public class CarouselViewPager extends ViewPager {
    // 是否可滑动
    private boolean isScrollable = true;

    // 是否无限循环滑动
    private boolean isCycle;

    public CarouselViewPager(@NonNull Context context, int autoPlayDuration) {
        super(context);
        init(autoPlayDuration);
    }


    public CarouselViewPager(@NonNull Context context) {
        super(context);
        init(-1);
    }

    public CarouselViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(-1);
    }


    private void init(int autoPlayDuration){
        int scrollDuration = ViewPagerScroller.DEFAULT_SCROLL_DURATION;
        if (autoPlayDuration < ViewPagerScroller.DEFAULT_SCROLL_DURATION){
            scrollDuration = autoPlayDuration / 2;
        }
        ViewPagerScroller scroller = new ViewPagerScroller(getContext(), scrollDuration);
        scroller.initViewPagerScroll(this);
        isScrollable = true;
    }

    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast, downX, downY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getParent()!=null){
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                downX = xLast = ev.getX();
                downY = yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                // 计算在X和Y方向的偏移量
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                ViewParent parent = getParent();
                if (parent != null) {
                    // 横向滑动小于纵向滑动时不拦截事件
                    if (xDistance < yDistance) {
                        parent.requestDisallowInterceptTouchEvent(false);
                    } else {
                        if (isCycle){  // 无限轮播
                            parent.requestDisallowInterceptTouchEvent(true);
                        } else {       // 非无限轮播时，第一张和最后一张要特殊处理
                            int currentItem = getCurrentItem();
                            if ((currentItem == 0 && curX > downX) || (currentItem == getChildCount() - 1 && curX < downX)){
                                parent.requestDisallowInterceptTouchEvent(false);
                            }
                        }

                    }
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isScrollable) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isScrollable) {
            return false;
        } else {
            return super.onTouchEvent(ev);
        }
    }

    public void setScrollable(boolean isScrollable) {
        this.isScrollable = isScrollable;
    }

    public void setCycle(boolean isCycle) {
        this.isCycle = isCycle;
    }
}
