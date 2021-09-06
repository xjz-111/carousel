package com.leslie.carousel_view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.leslie.carousel.BaseCarouselViewModel;
import com.leslie.carousel.CarouselView;
import com.leslie.carousel_view.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements CarouselView.OnPageChangeListener {
    private ActivityMainBinding binding;
    private TestVm1 vm1;
    private TestVm2 vm2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_main, null, false);
        setContentView(binding.getRoot());


        vm1 = new TestVm1(Utils.getList1());
        vm2 = new TestVm2();
        binding.cv.setViewModel(vm1, vm2).notifyDataSetChanged();
        vm2.setList(Utils.getList2());
        binding.cv.notifyDataSetChanged();

        binding.cv.addOnPageChangeListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.cv.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.cv.onPause();
    }


    @Override
    public void onPageSelected(BaseCarouselViewModel vm, int position) {
        Log.i("xjzhao", "position : " + position);
    }

    @Override
    public void onPageScrollStateChanged(BaseCarouselViewModel vm, int index) {

    }

    @Override
    public void onPageScrolled(BaseCarouselViewModel vm, int i, float v, int i2) {
        Log.d("xjzhao", "v : " + v + "  i2 : " + i2);
    }

}
