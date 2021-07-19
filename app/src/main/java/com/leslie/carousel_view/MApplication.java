package com.leslie.carousel_view;

import android.app.Application;

import com.bumptech.glide.request.target.ViewTarget;

/**
 * 作者：xjzhao
 * 时间：2021-07-19 18:10
 */
public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 解决Glide加载图片设置tag的问题：You must not call setTag() on a view Glide is targeting
        ViewTarget.setTagId(R.id.glideIndexTag);
    }


}
