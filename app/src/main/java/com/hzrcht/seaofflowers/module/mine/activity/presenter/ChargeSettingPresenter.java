package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserChargeBean;
import com.xuexiang.xhttp2.XHttp;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

@SuppressLint("CheckResult")
public class ChargeSettingPresenter extends BaseViewPresenter<ChargeSettingViewer> {

    public ChargeSettingPresenter(ChargeSettingViewer viewer) {
        super(viewer);
    }

    public void getUserCharge() {
        XHttp.post(ApiServices.USERCHARGE)
                .accessToken(true)
                .execute(UserChargeBean.class)
                .subscribeWith(new TipRequestSubscriber<UserChargeBean>() {
                    @Override
                    protected void onSuccess(UserChargeBean userChargeBean) {
                        assert getViewer() != null;
                        getViewer().getUserChargeSuccess(userChargeBean);
                    }
                });
    }

    public void userEditCharge(String video_amount, String lang_amount, String look_amount) {
        if (TextUtils.isEmpty(video_amount)) {
            ToastUtils.show("请设置视频聊天费用");
            return;
        }
        if (TextUtils.isEmpty(lang_amount)) {
            ToastUtils.show("请设置文字聊天费用");
            return;
        }
        if (TextUtils.isEmpty(look_amount)) {
            ToastUtils.show("请设置查看手机号码费用");
            return;
        }

        XHttp.post(ApiServices.USEREDITCHARGE)
                .params("video_amount", video_amount)
                .params("lang_amount", lang_amount)
                .params("look_amount", look_amount)
                .accessToken(true)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().userEditChargeSuccess();
                    }
                });
    }


}