package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.SysGiftBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserIsAnchorBean;
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.xuexiang.xhttp2.XHttp;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class MineChatPresenter extends BaseViewPresenter<MineChatViewer> {

    public MineChatPresenter(MineChatViewer viewer) {
        super(viewer);
    }

    public void getSysGift(ChatLayout mChatLayout) {
        XHttp.post(ApiServices.SYSGIFT)
                .accessToken(true)
                .execute(SysGiftBean.class)
                .subscribeWith(new TipRequestSubscriber<SysGiftBean>() {
                    @Override
                    protected void onSuccess(SysGiftBean sysGiftBean) {
                        assert getViewer() != null;
                        getViewer().getSysGiftSuccess(sysGiftBean, mChatLayout);
                    }
                });
    }

    public void sendGift(String anchor_id, String gift_id, ChatLayout mChatLayout, SysGiftBean.ResultBean resultBean) {
        XHttp.post(ApiServices.GIFTGIVE)
                .params("anchor_id", anchor_id)
                .params("gift_id", gift_id)
                .accessToken(true)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().sendGiftSuccess(mChatLayout, resultBean);
                    }
                });
    }

    public void getIsAnchor() {
        XHttp.post(ApiServices.USERISANCHOR)
                .accessToken(true)
                .execute(UserIsAnchorBean.class)
                .subscribeWith(new TipRequestSubscriber<UserIsAnchorBean>() {
                    @Override
                    protected void onSuccess(UserIsAnchorBean userIsAnchorBean) {
                        assert getViewer() != null;
                        getViewer().getIsAnchorSuccess(userIsAnchorBean);
                    }
                });
    }

    public void chatStart(String anchor_id, String content) {
        XHttp.post(ApiServices.CHATSTART)
                .params("anchor_id", anchor_id)
                .params("content", content)
                .accessToken(true)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().chatStartSuccess();
                    }
                });
    }
}