package com.hzrcht.seaofflowers.module.home.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.home.bean.HomeSearchBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;


public class HomeSearchPresenter extends BaseViewPresenter<HomeSearchViewer> {

    public HomeSearchPresenter(HomeSearchViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void getUserSearch(String name) {
        XHttp.post(ApiServices.USERSEARCH)
                .params("name", name)
                .accessToken(true)
                .execute(HomeSearchBean.class)
                .subscribeWith(new TipRequestSubscriber<HomeSearchBean>() {
                    @Override
                    protected void onSuccess(HomeSearchBean homeSearchBean) {
                        assert getViewer() != null;
                        getViewer().getUserSearchSuccess(homeSearchBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().getUserSearchFail(apiException.getDisplayMessage());
                    }
                });
    }
}