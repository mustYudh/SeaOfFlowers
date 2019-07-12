package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.data.UserProfile;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.SysLabelBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserPhotoListBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;

import java.util.ArrayList;
import java.util.List;
@SuppressLint("CheckResult")
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


  public void getSysLabel() {
    XHttp.post(ApiServices.SYSLABEL)
            .accessToken(true)
            .params("sex", UserProfile.getInstance().getUserSex()+"")
            .execute(SysLabelBean.class)
            .subscribeWith(new TipRequestSubscriber<SysLabelBean>() {
              @Override
              protected void onSuccess(SysLabelBean sysLabelBean) {
                assert getViewer() != null;
                getViewer().getSysLabelSuccess(sysLabelBean);
              }

              @Override
              protected void onError(ApiException apiException) {
                super.onError(apiException);
              }
            });
  }

}