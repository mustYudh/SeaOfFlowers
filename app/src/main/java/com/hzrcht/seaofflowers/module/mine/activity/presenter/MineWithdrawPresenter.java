package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserAccountsBean;
import com.hzrcht.seaofflowers.module.mine.bean.SysMoneyBean;
import com.hzrcht.seaofflowers.module.mine.bean.UserAmountBean;
import com.xuexiang.xhttp2.XHttp;
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
                });
    }


    public void userWithdraw(int type, String pay_id) {
        XHttp.post(ApiServices.USERWITHDRAW)
                .params("type", type)
                .params("pay_id", pay_id)
                .accessToken(true)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().userWithdrawSuccess();
                    }
                });
    }

    public void getUserAmount() {
        XHttp.post(ApiServices.USERAMOUNT)
                .accessToken(true)
                .execute(UserAmountBean.class)
                .subscribeWith(new TipRequestSubscriber<UserAmountBean>() {
                    @Override
                    protected void onSuccess(UserAmountBean amountBean) {
                        assert getViewer() != null;
                        getViewer().getUserAmountSuccess(amountBean);
                    }
                });
    }
}