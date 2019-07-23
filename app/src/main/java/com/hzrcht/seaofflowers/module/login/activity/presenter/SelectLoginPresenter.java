package com.hzrcht.seaofflowers.module.login.activity.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.login.bean.LoginBean;
import com.hzrcht.seaofflowers.module.login.bean.WechatInfo;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.callback.SimpleCallBack;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class SelectLoginPresenter extends BaseViewPresenter<SelectLoginViewer> {
    public SelectLoginPresenter(SelectLoginViewer viewer) {
        super(viewer);
    }

    public void loginThird(String type, String unionid, String openid, String data) {
        XHttp.post(ApiServices.LOGINTHIRD)
                .params("type", type)
                .params("unionid", unionid)
                .params("openid", openid)
                .params("data", data)
                .execute(LoginBean.class)
                .subscribeWith(new TipRequestSubscriber<LoginBean>() {
                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        assert getViewer() != null;
                        getViewer().getWxInfoSuccess(loginBean);
                    }
                });
    }


    public void loginWechat(String token, String openid) {
        XHttp.get("/sns/userinfo")
                .baseUrl("https://api.weixin.qq.com")
                .params("access_token", token)
                .params("openid", openid)
                .syncRequest(false)
                .onMainThread(true)
                .execute(new SimpleCallBack<WechatInfo>() {
                    @Override
                    public void onSuccess(WechatInfo response) throws Throwable {
                        Log.e("aaa", response.toString());
                    }

                    @Override
                    public void onError(ApiException e) {
                        Log.e("aaa", "失败");
                    }
                });
    }
}
