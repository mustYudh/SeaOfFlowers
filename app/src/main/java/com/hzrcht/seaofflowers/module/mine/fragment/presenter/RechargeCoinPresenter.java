package com.hzrcht.seaofflowers.module.mine.fragment.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.bean.PayInfo;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.bean.SysMoneyBean;
import com.hzrcht.seaofflowers.module.mine.bean.UserAmountBean;
import com.xuexiang.xhttp2.XHttp;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class RechargeCoinPresenter extends BaseViewPresenter<RechargeCoinViewer> {

    public RechargeCoinPresenter(RechargeCoinViewer viewer) {
        super(viewer);
    }

    public void getSysMoney() {
        XHttp.post(ApiServices.SYSLANMU)
                .params("type", "1")
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


    public void orderAdd(String type, String pay_id) {
        XHttp.post(ApiServices.ORDERADD)
                .params("type", type)
                .params("pay_id", pay_id)
                .accessToken(true)
                .execute(PayInfo.class)
                .subscribeWith(new TipRequestSubscriber<PayInfo>() {
                    @Override
                    protected void onSuccess(PayInfo payInfo) {
                        assert getViewer() != null;
                        getViewer().orderAddSuccess(payInfo);
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