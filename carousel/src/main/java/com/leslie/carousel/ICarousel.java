package com.leslie.carousel;

import androidx.viewpager.widget.ViewPager;

/**
 * 作者：xjzhao
 * 时间：2021-07-19 10:44
 */
interface ICarousel {
    /**
     * 是否可以循环
     * @param isCycle
     * @return
     */
    CarouselView setCycle(boolean isCycle);


    /**
     * 设置是否可以自动轮播
     * @param isAutoPlay
     * @return
     */
    CarouselView setAutoPlay(boolean isAutoPlay);

    /**
     * 设置自动轮播时间
     * @param time
     */
    CarouselView setAutoPlayDuration(int time);


    /**
     * 设置切换动画效果
     * @param tf
     */
    void setPageTransformer(ViewPager.PageTransformer tf);

    /**
     * 获取设定最大数据
     * @return
     */
    int getMaxCount();


    /**
     * 处理两张图片切换时的闪白问题
     */
    void onFlingWhite();

    /**
     * 切换
     * @param index
     */
    void onPageChange(int index);

    /**
     * 开启轮播
     */
    void startPlay();

    /**
     * 停止轮播
     */
    void stopPlay();

    /**
     * 刷新
     */
    void notifyDataSetChanged();

    void addOnPageChangeListener(CarouselView.OnPageChangeListener l);

    void onResume();

    void onPause();
}
