package com.hzrcht.seaofflowers.module.home.fragment.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.NoTipRequestSubscriber;
import com.hzrcht.seaofflowers.module.home.bean.HomeAnchorListBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class HomeNewrPresenter extends BaseViewPresenter<HomeNewrViewer> {

    public HomeNewrPresenter(HomeNewrViewer viewer) {
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
}