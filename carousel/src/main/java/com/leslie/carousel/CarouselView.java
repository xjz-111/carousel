package com.leslie.carousel;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * 默认开启自动轮播，开启自动轮播时若只有一个view，则不轮播
 *
 * 作者：xjzhao
 * 时间：2021-07-19 10:43
 */
public class CarouselView extends RelativeLayout implements ICarousel, ViewPager.OnPageChangeListener {
    /**
     * 配置属性
     **/
    private boolean isCycle;
    private boolean isAutoPlay;
    private int autoPlayDuration;
    private int changeDuration;
    protected CarouselViewPager viewPager;
    protected CarouselViewAdapter adapter;
    private boolean isAutoPlaying;
    private Handler handler;
    private boolean isNotifyDataSetChanged;
    private LayoutInflater inflate;
    private SparseArray<Vm> vmSparseArray = new SparseArray<>();
    private BaseCarouselViewModel[] vms;
    private int count;
    private OnPageChangeListener onPageChangeListener;


    public CarouselView setViewModel(BaseCarouselViewModel... vms){
        this.vms = vms;
        return this;
    }

    public CarouselView(Context context) {
        super(context);
        init(context, null);
    }

    public CarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CarouselView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CarouselView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CarouselView);
        if (null != a) {
            isCycle = a.getBoolean(R.styleable.CarouselView_isCycle, true);
            isAutoPlay = a.getBoolean(R.styleable.CarouselView_isAutoPlay, true);
            autoPlayDuration = a.getInt(R.styleable.CarouselView_autoPlayDuration, autoPlayDuration);
            changeDuration = a.getInt(R.styleable.CarouselView_changeDuration, ViewPagerScroller.DEFAULT_SCROLL_DURATION);
            a.recycle();
        }
        handler = new Handler();
        inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public final void onFlingWhite() {
        if (isCycle && null != vmSparseArray) {
            if (1 == vmSparseArray.size()){
                vmSparseArray.get(0).getViewModel().onDeoublData();
            }else if (2 == vmSparseArray.size()){
                Vm vm1 = vmSparseArray.get(0);
                Vm vm2 = vmSparseArray.get(1);
                vmSparseArray.put(2, vm1.setStartPosition(count));
                vmSparseArray.put(3, vm2.setStartPosition(count + vm1.getViewModel().getItemCount()));
            }
        }
    }

    @Override
    public int getRealSize() {
        int count = 0;
        int size = vmSparseArray.size();
        for(int i = 0; i < size; i++) {
            int key = vmSparseArray.keyAt(i);
            Vm vm = vmSparseArray.get(key);
            if (null != vm && null != vm.getViewModel()){
                count += vm.getViewModel().getItemCount();
            }
        }
        return count;
    }

    @Override
    public void onPageChange(int index) {

    }

    @Override
    public final void startPlay() {
        if (count > 1 && !isAutoPlaying) {
            isAutoPlaying = true;
            handler.postDelayed(autoPlayRunnable, autoPlayDuration);
        }
    }

    @Override
    public final void stopPlay() {
        if (count > 1 && isAutoPlaying) {
            isAutoPlaying = false;
            handler.removeCallbacks(autoPlayRunnable);
        }
    }


    @Override
    public final void setPageTransformer(ViewPager.PageTransformer tf) {

    }

    @Override
    public final int getMaxCount() {
        if (isCycle) {
            //循环情况下如果单张不让可滑动，这里改为：count > 1 ? Integer.MAX_VALUE / 2 : 1;
            return count > 0 ? Integer.MAX_VALUE / 2 : 0;
        }else {
            return count;
        }
    }

    @Override
    public final void onPageScrolled(int i, float v, int i2) {
        if (null != onPageChangeListener){
            Vm vm = getVm(i % count);
            onPageChangeListener.onPageScrolled(null != vm ? vm.getViewModel() : null, i % count, v, i2);
        }
    }

    @Override
    public final void onPageSelected(int index) {
        if (0 != count) {
            onPageChange(index % count);
            if (null != adapter){
                adapter.onPageSelected(index);
            }

            if (null != onPageChangeListener){
                Vm vm = getVm(index % count);
                onPageChangeListener.onPageSelected(null != vm ? vm.getViewModel() : null, index % count);
            }
        }
    }

    @Override
    public final void onPageScrollStateChanged(int index) {
        if (null != onPageChangeListener){
            Vm vm = getVm(index % count);
            onPageChangeListener.onPageScrollStateChanged(null != vm ? vm.getViewModel() : null, index % count);
        }
    }

    private Runnable autoPlayRunnable = new Runnable() {
        @Override
        public void run() {
            if (null != viewPager) {
                int currentItem = viewPager.getCurrentItem();
                currentItem++;
                if (currentItem >= getMaxCount()) {
                    currentItem = 0;
                } else if (currentItem <= 0) {
                    currentItem = getMaxCount();
                }
                viewPager.setCurrentItem(currentItem, true);
                isAutoPlaying = false;
                startPlay();
            }
        }
    };

    @Override
    public final boolean dispatchTouchEvent(MotionEvent ev) {
        // 设置手指触碰时停止自动切换
        if (isAutoPlay) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    stopPlay();
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    startPlay();
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isAutoPlay) {
            stopPlay();
        }
    }

    @Override
    public void notifyDataSetChanged() {
        resize();

        //如果是无限循环的，刷新是必须全部重新布局，因为数量太大，直接更新会出现卡顿
        if (isCycle || null == viewPager){
            removeAllViews();
            viewPager = null;
            viewPager = new CarouselViewPager(getContext(), autoPlayDuration, changeDuration);
            viewPager.setCycle(isCycle);
            viewPager.setOffscreenPageLimit(5);
            viewPager.addOnPageChangeListener(this);
            viewPager.setAdapter(adapter = new CarouselViewAdapter());
            addView(viewPager, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        if (!isCycle) {
            adapter.notifyDataSetChanged();
        }
        isNotifyDataSetChanged = true;
        int cycleStart = count != 0 ? (int) (Math.floor(Integer.MAX_VALUE / 4 / count) * count) : 0;
        viewPager.setCurrentItem(isCycle ? cycleStart : 0);
        if (isAutoPlay) {
            startPlay();
        }
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener l) {
        this.onPageChangeListener = l;
    }

    @Override
    public void onResume() {
        startPlay();
        for(int i = 0; i < vmSparseArray.size(); i++) {
            int key = vmSparseArray.keyAt(i);
            Vm vm = vmSparseArray.get(key);
            BaseCarouselViewModel viewModel = vm.getViewModel();
            if (null != viewModel){
                viewModel.onResume();
            }
        }

    }

    @Override
    public void onPause() {
        stopPlay();
        for(int i = 0; i < vmSparseArray.size(); i++) {
            int key = vmSparseArray.keyAt(i);
            Vm vm = vmSparseArray.get(key);
            BaseCarouselViewModel viewModel = vm.getViewModel();
            if (null != viewModel){
                viewModel.onPause();
            }
        }
    }

    @Override
    public CarouselView setCycle(boolean cycle) {
        isCycle = cycle;
        return this;
    }

    @Override
    public CarouselView setAutoPlay(boolean isAutoPlay) {
        this.isAutoPlay = isAutoPlay;
        return this;
    }

    @Override
    public CarouselView setAutoPlayDuration(int time) {
        this.autoPlayDuration = time;
        return this;
    }

    private Vm getVm(final int position){
        int size = vmSparseArray.size();
        int total = 0;
        int lastTotal = total;
        Vm vm;
        for (int i = 0; i < size; i++) {
            total += (vm = vmSparseArray.get(i)).getViewModel().getItemCount();
            if (position >= lastTotal && position < total) {
                return vm;
            }
            lastTotal = total;
        }
        return null;
    }

    private void resize(){
        vmSparseArray.clear();
        count = 0;
        onFlingWhite();
        int length;
        if (null != vms && (length = vms.length) > 0) {
            BaseCarouselViewModel viewModel;
            for (int i = 0; i < length; i++) {
                if (null != (viewModel = vms[i])) {
                    vmSparseArray.put(i, new Vm(viewModel.setContext(getContext()).setAdapter(this).setType(i + 1), count));
                    count += viewModel.getItemCount();
                }
            }
        }
    }


    /**
     * Adapter
     */
    final class CarouselViewAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = null;
            if (0 != count){
                final int realPosition = position % count;
                Vm vm = getVm(realPosition);
                if (null != vm){
                    final BaseCarouselViewModel viewModel = vm.getViewModel();
                    final int positionInViewModel = realPosition - vm.getStartPosition();
                    if (null != viewModel) {
                        ViewDataBinding binding = DataBindingUtil.inflate(inflate, viewModel.getItemLayoutId(), null, false);//viewModel.getItemView(realPosition);
                        view = binding.getRoot();
                        container.addView(view);
                        vm.setBinding(binding);
                        final Object item = viewModel.getItem(positionInViewModel);
                        if (null != item){
                            viewModel.initView(binding, position, positionInViewModel, item);
                            if (viewModel.isDatabinding()) setVariable(binding, viewModel, item, position);
                            view.setTag(item);
                        }

                        view.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewModel.onClick(position, positionInViewModel , viewModel.getItem(positionInViewModel));
                            }
                        });
                        view.setOnLongClickListener(new OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                return viewModel.onLongClick(position, positionInViewModel, viewModel.getItem(positionInViewModel));
                            }
                        });
                    }

                    if (isNotifyDataSetChanged && 0 == realPosition){
                        isNotifyDataSetChanged = false;
                        onPageSelected(0);
                        CarouselView.this.onPageSelected(0);
                    }
                }

            }
            return view;
        }

        void setVariable(@NonNull ViewDataBinding binding, @NonNull BaseCarouselViewModel vm, Object item, final int position){
            int variable = vm.getVariable(item, position, position % count);
            if (variable > 0) {
                binding.setVariable(variable, item);
                binding.executePendingBindings();
            }
        }

        final void onPageSelected(int position){
            if (0 != count) {
                int realPosition = position % count;
                Vm vm = getVm(realPosition);
                if (null != vm){
                    BaseCarouselViewModel viewModel = vm.getViewModel();
                    int positionInViewModel = realPosition - vm.getStartPosition();
                    if (null != viewModel){
                        viewModel.onPageSelected(realPosition, positionInViewModel, viewModel.getItem(positionInViewModel));
                    }
                }
            }
        }

        @Override
        public int getCount() {
            return getMaxCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    public interface OnPageChangeListener {
        void onPageSelected(BaseCarouselViewModel vm, int position);

        void onPageScrollStateChanged(BaseCarouselViewModel vm, int index);

        void onPageScrolled(BaseCarouselViewModel vm, int i, float v, int i2);
    }
}

