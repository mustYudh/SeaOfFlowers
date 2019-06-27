package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.UserPhotoListBean;
import com.yu.common.framework.BaseViewPresenter;
import java.util.ArrayList;
import java.util.List;

public class MineRedactDataPresenter extends BaseViewPresenter<MineRedactDataViewer> {

  public MineRedactDataPresenter(MineRedactDataViewer viewer) {
    super(viewer);
  }

  /**
   * 模拟网络请求获取图片
   */
  public void getPhotoList() {
    List<UserPhotoListBean> list = new ArrayList<>();
    assert getViewer() != null;
    getViewer().getPhotoList(list);
  }
}