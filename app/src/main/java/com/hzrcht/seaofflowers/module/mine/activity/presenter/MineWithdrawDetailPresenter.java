package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserBillListBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;


public class MineWithdrawDetailPresenter extends BaseViewPresenter<MineWithdrawDetailViewer> {

    public MineWithdrawDetailPresenter(MineWithdrawDetailViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void getUserBill(String page, String pageSize, String month) {
        XHttp.post(ApiServices.BILLWITHDRAW)
                .params("page", page)
                .params("pageSize", pageSize)
                .params("month", month)
                .accessToken(true)
                .execute(UserBillListBean.class)
                .subscribeWith(new TipRequestSubscriber<UserBillListBean>() {
                    @Override
                    protected void onSuccess(UserBillListBean userBillListBean) {
                        assert getViewer() != null;
                        getViewer().getUserBillSuccess(userBillListBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().getUserBillFail();
                    }
                });
    }
}