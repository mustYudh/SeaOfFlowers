package com.hzrcht.seaofflowers.module.home.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserIsAnchorBean;
import com.xuexiang.xhttp2.XHttp;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class HomeVideoWaitPresenter extends BaseViewPresenter<HomeVideoWaitViewer> {

    public HomeVideoWaitPresenter(HomeVideoWaitViewer viewer) {
        super(viewer);
    }

    public void getIsAnchor(String user_id) {
        XHttp.post(ApiServices.USERISANCHOR)
                .accessToken(true)
                .params("user_id",user_id)
                .execute(UserIsAnchorBean.class)
                .subscribeWith(new TipRequestSubscriber<UserIsAnchorBean>() {
                    @Override
                    protected void onSuccess(UserIsAnchorBean userIsAnchorBean) {
                        assert getViewer() != null;
                        getViewer().getIsAnchorSuccess(userIsAnchorBean);
                    }
                });
    }
}