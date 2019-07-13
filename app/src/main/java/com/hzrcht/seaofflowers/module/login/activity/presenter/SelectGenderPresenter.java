package com.hzrcht.seaofflowers.module.login.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.login.bean.LoginBean;
import com.hzrcht.seaofflowers.module.login.bean.UserSigBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class SelectGenderPresenter extends BaseViewPresenter<SelectGenderViewer> {
    public SelectGenderPresenter(SelectGenderViewer viewer) {
        super(viewer);
    }

    public void selectSex(String sex) {
        XHttp.post(ApiServices.SELECTSEX)
                .params("sex", sex)
                .accessToken(true)
                .execute(LoginBean.class)
                .subscribeWith(new TipRequestSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object object) {
                        assert getViewer() != null;
                        getViewer().selectSexSuccess();
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().selectSexFail();
                    }
                });
    }

    public void getUserSig() {
        XHttp.post(ApiServices.GETUSERSIG)
                .accessToken(true)
                .execute(UserSigBean.class)
                .subscribeWith(new TipRequestSubscriber<UserSigBean>() {
                    @Override
                    protected void onSuccess(UserSigBean userSigBean) {
                        assert getViewer() != null;
                        getViewer().getUserSigSuccess(userSigBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().getUserSigFail();
                    }
                });
    }
}
