package com.leslie.carousel;

import android.content.Context;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：xjzhao
 * 时间：2021-07-19 10:55
 */
public abstract class BaseCarouselViewModel<T, K extends ViewDataBinding> {
    private List<T> list = new ArrayList<>();
    protected SparseArray<K> views = new SparseArray<>();
    private Context context;
    private CarouselView adapter;
    private int type;
    private int startPosition;

    public BaseCarouselViewModel() {
    }

    public BaseCarouselViewModel(List<T> list) {
        this.list = list;
    }

    public BaseCarouselViewModel<T, K> setContext(Context context) {
        this.context = context;
        return this;
    }

    public final T getItem(int positionInViewModel) {
        if (positionInViewModel > -1 && getItemCount() > positionInViewModel) {
            return list.get(positionInViewModel);
        }
        return null;
    }

    public BaseCarouselViewModel<T, K> setList(List<T> list) {
        if (null != list) {
            this.list.clear();
            this.list.addAll(list);
        }
        return this;
    }

    public BaseCarouselViewModel<T, K> addItem(T t) {
        list.add(t);
        return this;
    }

    public BaseCarouselViewModel<T, K> remove(int positionInViewModel) {
        if (positionInViewModel > -1 && getItemCount() > positionInViewModel) {
            list.remove(positionInViewModel);
        }
        return this;
    }

    public BaseCarouselViewModel<T, K> remove(T t) {
        list.remove(t);
        return this;
    }

    public final List<T> getList() {
        return list;
    }

    public final Context getContext() {
        return context;
    }

    final BaseCarouselViewModel<T, K> setAdapter(CarouselView adapter) {
        this.adapter = adapter;
        return this;
    }

    final BaseCarouselViewModel<T, K> setType(int type){
        this.type = type;
        return this;
    }

    final int getType(){
        return type;
    }

    protected int getStartPosition() {
        return startPosition;
    }

    protected void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * 数据double
     *
     * @return
     */
    final BaseCarouselViewModel<T, K> onDeoublData() {
        List<T> temp = new ArrayList<>();
        temp.addAll(list);
        list.addAll(temp);
        return this;
    }

    /**
     * 当前ViewModel对应有多少项
     *
     * @return
     */
    public final int getItemCount() {
        return list.size();
    }

    protected abstract int getItemLayoutId();


    /**
     * 用Databinding时这里可以不处理，否则这里需要初始化View
     * @param binding
     * @param position
     * @param t
     */
    protected void initView(@NonNull K binding, final int position, final int positionInViewModel, final T t){
        views.put(positionInViewModel, binding);
    }

    private K getViewBinding(int positionInViewModel){
        return views.get(positionInViewModel);
    }

    private void putViewBinding(int positionInViewModel, K binding){
        views.put(positionInViewModel, binding);
    }

    /**
     * 当前页面被选中时的处理
     * @param position
     * @param positionInViewModel
     * @param t
     */
    public void onPageSelected(final int position, final int positionInViewModel, final T t){

    }

    /**
     * 点击事件
     * @param position
     * @param positionInViewModel
     * @param t
     */
    protected void onClick(final int position, final int positionInViewModel, final T t){

    }

    /**
     * 长按事件
     * @param position
     * @param positionInViewModel
     * @param t
     * @return
     */
    protected boolean onLongClick(final int position, final int positionInViewModel, final T t){
        return false;
    }


    /**
     * 是否开启Databinding对View内容赋值
     * @return
     */
    protected boolean isDatabinding(){
        return true;
    }


    public abstract int getVariable(T item, int position, final int positionInViewModel);

    /**
     * 整个页面的onResume调用
     */
    public final void onResume() {
        for(int i = 0; i < views.size(); i++) {
            int key = views.keyAt(i);
            K binding = views.get(key);
            onResume(binding, key + getStartPosition(), key);
        }
    }

    /**
     * 整个页面的onPause调用
     */
    public final void onPause() {
        for(int i = 0; i < views.size(); i++) {
            int key = views.keyAt(i);
            K binding = views.get(key);
            onPause(binding, key + getStartPosition(), key);
        }
    }


    /**
     * 每个ViewModel在onResume时做自己的处理
     * @param k
     * @param position            全局位置
     * @param positionInViewMode  当前type的位置
     */
    protected void onResume(K k, int position, int positionInViewMode){

    }

    /**
     * 每个ViewModel在onPause时做自己的处理
     * @param k
     * @param position            全局位置
     * @param positionInViewMode  当前type的位置
     */
    protected void onPause(K k, int position, int positionInViewMode){

    }
}
