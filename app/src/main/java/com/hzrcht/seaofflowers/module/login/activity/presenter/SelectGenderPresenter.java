package com.hzrcht.seaofflowers.module.login.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.login.bean.LoginBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;

public class SelectGenderPresenter extends BaseViewPresenter<SelectGenderViewer> {
    public SelectGenderPresenter(SelectGenderViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void selectSex(String sex) {
        XHttp.post("httuahai.hzrcht.com/api/user/sex")
                .params("sex", sex)
                .accessToken(false)
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
