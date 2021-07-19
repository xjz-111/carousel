#### carousel
```
碎片化的轮播图，结合Databinding，使用时只需关注定义每种类型的ViewModel 
```
#### 一. 功能介绍
* 支持多种格式，每种格式一个ViewModel，安顺序组装，每种ViewModel对应一个View
* 支持轮播，单个View时即使开启轮播，默认也不会自动轮播，但可滑动切换重复显示当个View
* 支持自定义切换动画及时间等
* 支持onResume和onPause(页面级，单个View的使用onPageSelected便可) 
* 默认使用Databinding，xml必须遵循。View数据填充可选择是否手动设置

#### 二. 使用
1. 添加依赖和配置
``` gradle
// 在project的build.gradle中添加
repositories {
    ...
    maven { url 'https://jitpack.io' }
}

dependencies {
    // 替换成最新版本
    implementation 'com.github.xjz-111:carousel:*.*.*'
    ...
}
```
2. 具体使用
```xml
<!-- 布局中定义，也可代码中初始化 -->
<com.leslie.carousel.CarouselView
            android:id="@+id/cv"
            android:layout_width="match_parent"
            android:layout_height="300dp"
            app:autoPlayDuration="3000"
            app:isAutoPlay="true"
            app:isCycle="true" />
```
``` java
// 继承BaseCarouselViewModel<T, K extens ViewDatabinding>，T为当前页面数据类型
public class TestVm extends BaseCarouselViewModel<DataBean, Vm2Binding> {

  @Override
  protected int getItemLayoutId() {
      return R.layout.vm2;
  }

  @Override
  public int getVariable(DataBean item, int position, final int positionInViewModel) {
      return BR.vm2;
  }  
  
  
  /*************************以下默认无需实现*****************************/
  
  // 开启后必须在initView()中初始化各个View的数据
  @Override
  protected boolean isDatabinding() {
    return super.isDatabinding();
  }

  // 可在此做初始化处理
  @Override
  protected void initView(@NonNull Vm1Binding binding, int position, int positionInViewModel, String s) {
    super.initView(binding, position, positionInViewModel, s);
  }

  // 页面onResume时回调ViewModel中各个item的onResume
  @Override
  protected void onResume(Vm1Binding vm1Binding, int position, int positionInViewMode) {
    super.onResume(vm1Binding, position, positionInViewMode);
  }

   // 页面onPause时回调ViewModel中各个item的onPause
  @Override
  protected void onPause(Vm1Binding vm1Binding, int position, int positionInViewMode) {
    super.onPause(vm1Binding, position, positionInViewMode);
  }

  // 点击事件
  @Override
  protected void onClick(int position, int positionInViewModel, String s) {
    super.onClick(position, positionInViewModel, s);
  }

  // 长按事件
  @Override
  protected boolean onLongClick(int position, int positionInViewModel, String s) {
    return super.onLongClick(position, positionInViewModel, s);
  }

  // 当前页面被显示
  @Override
  public void onPageSelected(int position, int positionInViewModel, String s) {
    super.onPageSelected(position, positionInViewModel, s);
  } 
}

// CarouselView所在的页面组装
vm1 = new TestVm1(Utils.getList1());
vm2 = new TestVm2();
carouselView.setViewModel(vm1 = new TestVm1(), vm2 = new TestVm2());
vm1.setList(list1);
vm2.setList(list2);
carouselView.notifyDataSetChanged();


// 如View中有类似视/音频等需要在页面onPause时暂停的，可在页面中如下调用，再在ViewModel对应名称的方法中做暂停/开启等处理
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
```

