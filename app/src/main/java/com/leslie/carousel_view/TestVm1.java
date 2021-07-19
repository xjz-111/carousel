package com.leslie.carousel_view;

import android.util.Log;

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
    protected void onResume(Vm1Binding vm1Binding, int position, int positionInViewMode) {
        super.onResume(vm1Binding, position, positionInViewMode);
        Log.i("xjzhao", this.getClass().getSimpleName() + " - onResume " + positionInViewMode);
    }

    @Override
    protected void onPause(Vm1Binding vm1Binding, int position, int positionInViewMode) {
        super.onPause(vm1Binding, position, positionInViewMode);
        Log.d("xjzhao", this.getClass().getSimpleName() + " - onPause " + position);
    }
}
