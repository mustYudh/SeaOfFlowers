package com.hzrcht.seaofflowers.module.message.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.message.bean.UserSysMessageBean;
import com.xuexiang.xhttp2.XHttp;
import com.yu.common.framework.BaseViewPresenter;


public class SystemMessagePresenter extends BaseViewPresenter<SystemMessageViewer> {

    public SystemMessagePresenter(SystemMessageViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void getUserSysMessage(int page, int pageSize) {
        XHttp.post(ApiServices.USERMESSAGE)
                .accessToken(true)
                .params("page", page + "")
                .params("pageSize", pageSize + "")
                .execute(UserSysMessageBean.class)
                .subscribeWith(new TipRequestSubscriber<UserSysMessageBean>() {
                    @Override
                    protected void onSuccess(UserSysMessageBean userSysMessageBean) {
                        assert getViewer() != null;
                        getViewer().getUserSysMessageSuccess(userSysMessageBean);
                    }
                });
    }
}