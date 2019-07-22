package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.home.bean.HomePayCoinBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class TRTCMainActivityPresenter extends BaseViewPresenter<TRTCMainActivityViewer> {

    public TRTCMainActivityPresenter(TRTCMainActivityViewer viewer) {
        super(viewer);
    }

    public void liveEnd(String live_id) {
        XHttp.post(ApiServices.LIVEEND)
                .params("live_id", live_id)
                .accessToken(true)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().liveEndSuccess();
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().liveEndFail(apiException.getDisplayMessage());
                    }
                });
    }

    public void livePayCoin(String live_id) {
        XHttp.post(ApiServices.LIVEPAYCOIN)
                .accessToken(true)
                .params("live_id", live_id)
                .execute(HomePayCoinBean.class)
                .subscribeWith(new TipRequestSubscriber<HomePayCoinBean>() {
                    @Override
                    protected void onSuccess(HomePayCoinBean homePayCoinBean) {
                        assert getViewer() != null;
                        getViewer().livePayCoinSuccess(homePayCoinBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().livePayCoinFail(apiException.getCode(), apiException.getDisplayMessage());
                    }
                });
    }

    public void attent(String anchor_id, String type) {
        XHttp.post(ApiServices.ATTENTCLICK)
                .accessToken(true)
                .params("anchor_id", anchor_id)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().attentSuccess(type);
                    }
                });
    }
}