package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserConfigBean;
import com.xuexiang.xhttp2.XHttp;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class SystemSettingsPresenter extends BaseViewPresenter<SystemSettingsViewer> {

    public SystemSettingsPresenter(SystemSettingsViewer viewer) {
        super(viewer);
    }

    public void getUserConfig() {
        XHttp.post(ApiServices.USERCONFIG)
                .accessToken(true)
                .execute(UserConfigBean.class)
                .subscribeWith(new TipRequestSubscriber<UserConfigBean>() {
                    @Override
                    protected void onSuccess(UserConfigBean userConfigBean) {
                        assert getViewer() != null;
                        getViewer().getUserConfigSuccess(userConfigBean);
                    }
                });
    }

    public void userEditConfig(boolean disturb,String type) {
        XHttp.post(ApiServices.USEREEDITCONFIG)
                .accessToken(true)
                .params("type",type)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().userEditConfigSuccess(disturb,type);
                    }
                });
    }
}