package com.leslie.carousel_view;

import android.util.Log;

import com.leslie.carousel.BR;
import com.leslie.carousel.BaseCarouselViewModel;
import com.leslie.carousel_view.databinding.Vm1Binding;
import com.leslie.carousel_view.databinding.Vm2Binding;

/**
 * 作者：xjzhao
 * 时间：2021-07-19 19:21
 */
public class TestVm2 extends BaseCarouselViewModel<String, Vm2Binding> {

    @Override
    protected int getItemLayoutId() {
        return R.layout.vm2;
    }

    @Override
    public int getVariable(String item, int position, final int positionInViewModel) {
        return BR.vm2;
    }
}
