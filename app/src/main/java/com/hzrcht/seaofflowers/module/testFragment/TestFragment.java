package com.hzrcht.seaofflowers.module.testFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.action.ActionHeads;
import com.hzrcht.seaofflowers.action.BaseActionHelper;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.yu.common.toast.ToastUtils;

/**
 * @author yudenghao
 * @date 2019-06-13
 */
public class TestFragment extends BaseFragment {
  @Override protected int getContentViewId() {
    return R.layout.test_fragment;
  }

  @Override protected void setView(@Nullable Bundle savedInstanceState) {

  }

  @Override protected void loadData() {
    bindText(R.id.test, "测试1");
    bindView(R.id.show_test, v -> {
      BaseActionHelper.with(getActivity())
          .handleAction(ActionHeads.HOME_CENTER_TAB_STATUS_SHOW);
    });
    bindView(R.id.hide_test, v -> {
      BaseActionHelper.with(getActivity())
          .handleAction(ActionHeads.HOME_CENTER_TAB_STATUS_HIDE);
    });
  }
}
