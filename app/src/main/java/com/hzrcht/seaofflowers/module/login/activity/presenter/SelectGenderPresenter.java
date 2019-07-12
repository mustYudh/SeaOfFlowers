package com.hzrcht.seaofflowers.module.login.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.login.bean.LoginBean;
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
                        super.onError(apiException);

                    }
                });
    }
}
