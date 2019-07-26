package com.hzrcht.seaofflowers.module.mine.fragment.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.bean.PayInfo;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.bean.SysMoneyBean;
import com.xuexiang.xhttp2.XHttp;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class DredgeVipPresenter extends BaseViewPresenter<DredgeVipViewer> {

    public DredgeVipPresenter(DredgeVipViewer viewer) {
        super(viewer);
    }

    public void getSysMoney() {
        XHttp.post(ApiServices.SYSLANMU)
                .params("type", "3")
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
}