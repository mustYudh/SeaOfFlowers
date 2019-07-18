package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.login.bean.LoginBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.countdown.RxCountDown;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.ui.DelayClickTextView;

@SuppressLint("CheckResult")
public class PhoneVerificationPresenter extends BaseViewPresenter<PhoneVerificationViewer> {

    public PhoneVerificationPresenter(PhoneVerificationViewer viewer) {
        super(viewer);
    }

    public void sendVerCode(String number, RxCountDown countDown, DelayClickTextView textView) {
        XHttp.post(ApiServices.SENDVERCODE)
                .params("type", "Bind")
                .params("phone", number)
                .accessToken(true)
                .execute(LoginBean.class)
                .subscribeWith(new TipRequestSubscriber<LoginBean>() {
                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        assert getViewer() != null;
                        getViewer().sendVerCodeSuccess();
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        textView.setClickable(true);

                        super.onError(apiException);
                        countDown.stop();
                    }
                });
    }


    public void bindUserPhone(String number, String code) {
        XHttp.post(ApiServices.USERPHONE)
                .params("code", code)
                .params("phone", number)
                .accessToken(true)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().bindUserPhoneSuccess();
                    }
                });
    }


}