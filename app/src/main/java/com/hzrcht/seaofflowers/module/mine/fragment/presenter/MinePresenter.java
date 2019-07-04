package com.hzrcht.seaofflowers.module.mine.fragment.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.bean.MineUserInfoBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;


public class MinePresenter extends BaseViewPresenter<MineViewer> {

    public MinePresenter(MineViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void userInfo() {
        XHttp.post("http://huahai.hzrcht.com/api/user/info")
                .accessToken(true)
                .execute(MineUserInfoBean.class)
                .subscribeWith(new TipRequestSubscriber<MineUserInfoBean>() {
                    @Override
                    protected void onSuccess(MineUserInfoBean mineUserInfoBean) {
                        assert getViewer() != null;
                        getViewer().userInfoSuccess(mineUserInfoBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        super.onError(apiException);

                    }
                });
    }
}