package com.hzrcht.seaofflowers.module.home.fragment.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.NoTipRequestSubscriber;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.home.bean.HomeFansBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.LiveStartBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class HomeFansPresenter extends BaseViewPresenter<HomeFansViewer> {

    public HomeFansPresenter(HomeFansViewer viewer) {
        super(viewer);
    }

    public void getFansList() {
        XHttp.post(ApiServices.GETUSERLIST)
                .accessToken(true)
                .execute(HomeFansBean.class)
                .subscribeWith(new NoTipRequestSubscriber<HomeFansBean>() {
                    @Override
                    protected void onSuccess(HomeFansBean homeFansBean) {
                        assert getViewer() != null;
                        getViewer().getFansListSuccess(homeFansBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().getFansListFail();
                    }
                });
    }

    public void liveStart(String anchor_id, HomeFansBean.RowBean item) {
        XHttp.post(ApiServices.LIVESTART)
                .params("anchor_id", anchor_id)
                .accessToken(true)
                .execute(LiveStartBean.class)
                .subscribeWith(new TipRequestSubscriber<LiveStartBean>() {
                    @Override
                    protected void onSuccess(LiveStartBean liveStartBean) {
                        assert getViewer() != null;
                        getViewer().liveStartSuccess(liveStartBean, item);
                    }
                });
    }
}