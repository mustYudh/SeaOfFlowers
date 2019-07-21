package com.hzrcht.seaofflowers.module.home.fragment.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.NoTipRequestSubscriber;
import com.hzrcht.seaofflowers.module.home.bean.HomeAnchorListBean;
import com.hzrcht.seaofflowers.module.home.bean.HomeBannerBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class HomeLimitPresenter extends BaseViewPresenter<HomeLimitViewer> {

    public HomeLimitPresenter(HomeLimitViewer viewer) {
        super(viewer);
    }

    public void getAnchorList(String type, int page, int pageSize) {
        XHttp.post(ApiServices.GETANCHORLIST)
                .accessToken(true)
                .params("type", type)
                .params("page", page + "")
                .params("pageSize", pageSize + "")
                .execute(HomeAnchorListBean.class)
                .subscribeWith(new NoTipRequestSubscriber<HomeAnchorListBean>() {
                    @Override
                    protected void onSuccess(HomeAnchorListBean homeAnchorListBean) {
                        assert getViewer() != null;
                        getViewer().getAnchorListSuccess(homeAnchorListBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().getAnchorListFail();
                    }
                });
    }


    public void getBannerList() {
        XHttp.post(ApiServices.GETBANNER)
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