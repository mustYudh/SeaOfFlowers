package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.GiftListBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class MinePresentPresenter extends BaseViewPresenter<MinePresentViewer> {

    public MinePresentPresenter(MinePresentViewer viewer) {
        super(viewer);
    }

    public void getGiftIndex(String user_id) {
        XHttp.post(ApiServices.GIFTINDEX)
                .accessToken(true)
                .params("user_id", user_id)
                .execute(GiftListBean.class)
                .subscribeWith(new TipRequestSubscriber<GiftListBean>() {
                    @Override
                    protected void onSuccess(GiftListBean giftListBean) {
                        assert getViewer() != null;
                        getViewer().getGiftIndexSuccess(giftListBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        super.onError(apiException);

                    }
                });
    }
}