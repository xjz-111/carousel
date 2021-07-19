package com.leslie.carousel;

import androidx.databinding.ViewDataBinding;

/**
 * 作者：xjzhao
 * 时间：2021-07-19 10:59
 */
class Vm {
    private BaseCarouselViewModel viewModel;
    private int startPosition;
    private ViewDataBinding binding;

    public Vm(BaseCarouselViewModel viewModel, int startPosition) {
        this.viewModel = viewModel;
        setStartPosition(startPosition);
    }

    BaseCarouselViewModel getViewModel() {
        return viewModel;
    }

    Vm setViewModel(BaseCarouselViewModel viewModel) {
        this.viewModel = viewModel;
        return this;
    }

    int getStartPosition() {
        return startPosition;
    }

    Vm setStartPosition(int startPosition) {
        this.startPosition = startPosition;
        viewModel.setStartPosition(startPosition);
        return this;
    }

    public Vm setBinding(ViewDataBinding binding) {
        this.binding = binding;
        return this;
    }

    public ViewDataBinding getBinding() {
        return binding;
    }
}
