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

    @Override
    protected void onResume(Vm2Binding vm1Binding, int position, int positionInViewMode) {
        super.onResume(vm1Binding, position, positionInViewMode);
        Log.i("xjzhao", this.getClass().getSimpleName() + " - onResume " + position);
    }

    @Override
    protected void onPause(Vm2Binding vm1Binding, int position, int positionInViewMode) {
        super.onPause(vm1Binding, position, positionInViewMode);
        Log.d("xjzhao", this.getClass().getSimpleName() + " - onPause " + position);
    }
}
