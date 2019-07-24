package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class MineSuggestionPresenter extends BaseViewPresenter<MineSuggestionViewer> {

    public MineSuggestionPresenter(MineSuggestionViewer viewer) {
        super(viewer);
    }

    public void userFeedback(String content) {
        XHttp.post(ApiServices.USERFEEDBACK)
                .params("content", content)
                .accessToken(true)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().userFeedbackSuccess();
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().userFeedbackFail(apiException.getDisplayMessage());
                    }
                });
    }
}