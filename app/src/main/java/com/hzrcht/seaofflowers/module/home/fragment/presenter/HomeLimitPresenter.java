package com.hzrcht.seaofflowers.module.home.fragment.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.subscriber.NoTipRequestSubscriber;
import com.hzrcht.seaofflowers.module.home.bean.AnchorListBean;
import com.hzrcht.seaofflowers.module.home.bean.HomeBannerBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;


public class HomeLimitPresenter extends BaseViewPresenter<HomeLimitViewer> {

    public HomeLimitPresenter(HomeLimitViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void getAnchorList(String type) {
        XHttp.post("http://huahai.hzrcht.com/api/user/anchorList")
                .accessToken(true)
                .params("type", type)
                .execute(AnchorListBean.class)
                .subscribeWith(new NoTipRequestSubscriber<AnchorListBean>() {
                    @Override
                    protected void onSuccess(AnchorListBean anchorListBean) {
                        assert getViewer() != null;
                        getViewer().getAnchorListSuccess(anchorListBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        super.onError(apiException);

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void getBannerList() {
        XHttp.post("http://huahai.hzrcht.com/api/sys/banner")
                .accessToken(true)
                .execute(HomeBannerBean.class)
                .subscribeWith(new NoTipRequestSubscriber<HomeBannerBean>() {
                    @Override
                    protected void onSuccess(HomeBannerBean homeBannerBean) {
                        assert getViewer() != null;
                        getViewer().getBannerListSuccess(homeBannerBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        super.onError(apiException);

                    }
                });
    }
}