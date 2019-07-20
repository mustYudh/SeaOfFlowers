package com.hzrcht.seaofflowers.module.home.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.home.bean.HomePayCoinBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserIsAnchorBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class HomeVideoWaitPresenter extends BaseViewPresenter<HomeVideoWaitViewer> {

    public HomeVideoWaitPresenter(HomeVideoWaitViewer viewer) {
        super(viewer);
    }

    public void getIsAnchor(String user_id) {
        XHttp.post(ApiServices.USERISANCHOR)
                .accessToken(true)
                .params("user_id", user_id)
                .execute(UserIsAnchorBean.class)
                .subscribeWith(new TipRequestSubscriber<UserIsAnchorBean>() {
                    @Override
                    protected void onSuccess(UserIsAnchorBean userIsAnchorBean) {
                        assert getViewer() != null;
                        getViewer().getIsAnchorSuccess(userIsAnchorBean);
                    }
                });
    }


    public void livePayCoin(String[] split) {
        XHttp.post(ApiServices.LIVEPAYCOIN)
                .accessToken(true)
                .params("live_id", split[4])
                .execute(HomePayCoinBean.class)
                .subscribeWith(new TipRequestSubscriber<HomePayCoinBean>() {
                    @Override
                    protected void onSuccess(HomePayCoinBean homePayCoinBean) {
                        assert getViewer() != null;
                        getViewer().livePayCoinSuccess(homePayCoinBean, split);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().livePayCoinFail(apiException.getCode(), apiException.getDisplayMessage(), split);
                    }
                });
    }

    public void liveEnd(String[] split,int code) {
        XHttp.post(ApiServices.LIVEEND)
                .params("live_id", split[4])
                .accessToken(true)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().liveEndSuccess(split,code);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().liveEndFail(apiException.getDisplayMessage(),code);
                    }
                });
    }
}