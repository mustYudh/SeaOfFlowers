package com.hzrcht.seaofflowers.module.message.fragment.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserChargeBean;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;
import com.xuexiang.xhttp2.XHttp;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class MessagePresenter extends BaseViewPresenter<MessageViewer> {

    public MessagePresenter(MessageViewer viewer) {
        super(viewer);
    }

    public void getUserCharge(String user_id, ConversationInfo messageInfo) {
        XHttp.post(ApiServices.USERCHARGE)
                .accessToken(true)
                .params("user_id", user_id)
                .execute(UserChargeBean.class)
                .subscribeWith(new TipRequestSubscriber<UserChargeBean>() {
                    @Override
                    protected void onSuccess(UserChargeBean userChargeBean) {
                        assert getViewer() != null;
                        getViewer().getUserChargeSuccess(userChargeBean,messageInfo);
                    }
                });
    }
}