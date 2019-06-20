package com.hzrcht.seaofflowers.module.testFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.yu.common.glide.ImageLoader;

/**
 * @author yudenghao
 * @date 2019-06-13
 */
public class TestFragment1 extends BaseFragment {
  @Override protected int getContentViewId() {
    return R.layout.test_fragment;
  }

  @Override protected void setView(@Nullable Bundle savedInstanceState) {

  }


  @Override protected void loadData() {
    bindText(R.id.test,"测试2");
    ImageLoader.getInstance().displayImage(bindView(R.id.image),"http://pic37.nipic.com/20140113/8800276_184927469000_2.png");
  }



}
