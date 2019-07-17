package com.hzrcht.seaofflowers.module.home.fragment.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.NoTipRequestSubscriber;
import com.hzrcht.seaofflowers.module.home.bean.HomeFansBean;
import com.xuexiang.xhttp2.XHttp;
import com.yu.common.framework.BaseViewPresenter;


public class HomeFansPresenter extends BaseViewPresenter<HomeFansViewer> {

    public HomeFansPresenter(HomeFansViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void getFansList() {
        XHttp.post(ApiServices.GETUSERLIST)
                .accessToken(true)
                .execute(HomeFansBean.class)
                .subscribeWith(new NoTipRequestSubscriber<HomeFansBean>() {
                    @Override
                    protected void onSuccess(HomeFansBean homeFansBean) {
                        assert getViewer() != null;
                        getViewer().getFansListSuccess(homeFansBean);
                    }
                });
    }
}