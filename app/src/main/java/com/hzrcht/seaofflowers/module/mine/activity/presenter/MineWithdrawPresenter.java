package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserAccountsBean;
import com.hzrcht.seaofflowers.module.mine.bean.SysMoneyBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class MineWithdrawPresenter extends BaseViewPresenter<MineWithdrawViewer> {

    public MineWithdrawPresenter(MineWithdrawViewer viewer) {
        super(viewer);
    }

    public void getUserAccounts() {
        XHttp.post(ApiServices.USERACCOUNTS)
                .accessToken(true)
                .execute(UserAccountsBean.class)
                .subscribeWith(new TipRequestSubscriber<UserAccountsBean>() {
                    @Override
                    protected void onSuccess(UserAccountsBean userAccountsBean) {
                        assert getViewer() != null;
                        getViewer().getUserAccountsSuccess(userAccountsBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {

                    }
                });
    }

    public void getSysMoney() {
        XHttp.post(ApiServices.SYSLANMU)
                .params("type", "2")
                .accessToken(true)
                .execute(SysMoneyBean.class)
                .subscribeWith(new TipRequestSubscriber<SysMoneyBean>() {
                    @Override
                    protected void onSuccess(SysMoneyBean sysMoneyBean) {
                        assert getViewer() != null;
                        getViewer().getSysMoneySuccess(sysMoneyBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {

                    }
                });
    }
}