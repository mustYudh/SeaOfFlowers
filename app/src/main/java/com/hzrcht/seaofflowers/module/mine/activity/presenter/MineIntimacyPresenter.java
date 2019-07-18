package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.AnchorUserInfoBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.request.PostRequest;
import com.yu.common.framework.BaseViewPresenter;


public class MineIntimacyPresenter extends BaseViewPresenter<MineIntimacyViewer> {

    public MineIntimacyPresenter(MineIntimacyViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void getUserInfo(String user_id) {
        PostRequest post = XHttp.post(ApiServices.USERINFO);
        post.params("user_id", user_id);
        post.accessToken(true)
                .execute(AnchorUserInfoBean.class)
                .subscribeWith(new TipRequestSubscriber<AnchorUserInfoBean>() {
                    @Override
                    protected void onSuccess(AnchorUserInfoBean anchorUserInfoBean) {
                        assert getViewer() != null;
                        getViewer().getUserInfoSuccess(anchorUserInfoBean);
                    }
                });
    }
}