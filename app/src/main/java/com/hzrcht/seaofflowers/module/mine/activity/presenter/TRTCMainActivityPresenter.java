package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
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
}