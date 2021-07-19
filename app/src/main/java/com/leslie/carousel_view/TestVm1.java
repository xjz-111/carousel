package com.leslie.carousel_view;

import android.util.Log;

import androidx.annotation.NonNull;

import com.leslie.carousel.BR;
import com.leslie.carousel.BaseCarouselViewModel;
import com.leslie.carousel_view.databinding.Vm1Binding;

import java.util.List;

/**
 * 作者：xjzhao
 * 时间：2021-07-19 11:07
 */
public class TestVm1 extends BaseCarouselViewModel<String, Vm1Binding> {

    public TestVm1(List<String> list) {
        super(list);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.vm1;
    }


    @Override
    public int getVariable(String item, int position, final int positionInViewModel) {
        return BR.vm1;
    }



    @Override
    protected boolean isDatabinding() {
        return super.isDatabinding();
    }

    @Override
    protected void initView(@NonNull Vm1Binding binding, int position, int positionInViewModel, String s) {
        super.initView(binding, position, positionInViewModel, s);
    }

    @Override
    protected void onResume(Vm1Binding vm1Binding, int position, int positionInViewMode) {
        super.onResume(vm1Binding, position, positionInViewMode);
    }

    @Override
    protected void onPause(Vm1Binding vm1Binding, int position, int positionInViewMode) {
        super.onPause(vm1Binding, position, positionInViewMode);
    }

    @Override
    protected void onClick(int position, int positionInViewModel, String s) {
        super.onClick(position, positionInViewModel, s);
    }

    @Override
    protected boolean onLongClick(int position, int positionInViewModel, String s) {
        return super.onLongClick(position, positionInViewModel, s);
    }

    @Override
    public void onPageSelected(int position, int positionInViewModel, String s) {
        super.onPageSelected(position, positionInViewModel, s);
    }
}
