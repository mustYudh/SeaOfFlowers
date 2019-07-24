package com.hzrcht.seaofflowers.module.message.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.bean.MineCallBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;


public class MineCallPresenter extends BaseViewPresenter<MineCallViewer> {

    public MineCallPresenter(MineCallViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void getLiveList(int page, int pageSize) {
        XHttp.post(ApiServices.GETLIVELIST)
                .params("page", page + "")
                .params("pageSize", pageSize + "")
                .execute(MineCallBean.class)
                .subscribeWith(new TipRequestSubscriber<MineCallBean>() {
                    @Override
                    protected void onSuccess(MineCallBean mineCallBean) {
                        assert getViewer() != null;
                        getViewer().getLiveListSuccess(mineCallBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().getLiveListFail();
                    }
                });
    }
}