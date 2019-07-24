package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.SysWeChatBean;
import com.xuexiang.xhttp2.XHttp;
import com.yu.common.framework.BaseViewPresenter;


public class InAuthenticationPresenter extends BaseViewPresenter<InAuthenticationViewer> {

    public InAuthenticationPresenter(InAuthenticationViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void getSysWechat() {
        XHttp.post(ApiServices.SYSWECHAT)
                .execute(SysWeChatBean.class)
                .subscribeWith(new TipRequestSubscriber<SysWeChatBean>() {
                    @Override
                    protected void onSuccess(SysWeChatBean sysWeChatBean) {
                        assert getViewer() != null;
                        getViewer().getSysWechatSuccess(sysWeChatBean);
                    }
                });
    }
}