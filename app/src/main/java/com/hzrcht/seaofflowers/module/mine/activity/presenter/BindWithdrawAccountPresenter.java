package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class BindWithdrawAccountPresenter extends BaseViewPresenter<BindWithdrawAccountViewer> {

    public BindWithdrawAccountPresenter(BindWithdrawAccountViewer viewer) {
        super(viewer);
    }

    public void userAddacc(int type, String realname, String account) {
        XHttp.post(ApiServices.USERADDACC)
                .accessToken(true)
                .params("type", type)
                .params("realname", realname)
                .params("account", account)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().userAddaccSuccess();
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        super.onError(apiException);
                    }
                });
    }
}