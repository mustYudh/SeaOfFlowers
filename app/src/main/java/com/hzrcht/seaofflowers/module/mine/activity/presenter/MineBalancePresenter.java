package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserBillListBean;
import com.xuexiang.xhttp2.XHttp;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class MineBalancePresenter extends BaseViewPresenter<MineBalanceViewer> {

    public MineBalancePresenter(MineBalanceViewer viewer) {
        super(viewer);
    }

    public void getUserBill(String page, String pageSize, String month,int type) {
        XHttp.post(ApiServices.BILLINDEX)
                .params("page", page)
                .params("pageSize", pageSize)
                .params("month", month)
                .params("type", type)
                .accessToken(true)
                .execute(UserBillListBean.class)
                .subscribeWith(new TipRequestSubscriber<UserBillListBean>() {
                    @Override
                    protected void onSuccess(UserBillListBean userBillListBean) {
                        assert getViewer() != null;
                        getViewer().getUserBillSuccess(userBillListBean);
                    }
                });
    }
}