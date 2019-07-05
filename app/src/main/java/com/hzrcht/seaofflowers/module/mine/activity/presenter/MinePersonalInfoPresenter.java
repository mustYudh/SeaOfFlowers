package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.AnchorUserInfoBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class MinePersonalInfoPresenter extends BaseViewPresenter<MinePersonalInfoViewer> {

    public MinePersonalInfoPresenter(MinePersonalInfoViewer viewer) {
        super(viewer);
    }

    public void getUserInfo(String user_id) {
        XHttp.post(ApiServices.USERINFO)
                .accessToken(true)
                .params("user_id", user_id)
                .execute(AnchorUserInfoBean.class)
                .subscribeWith(new TipRequestSubscriber<AnchorUserInfoBean>() {
                    @Override
                    protected void onSuccess(AnchorUserInfoBean anchorUserInfoBean) {
                        assert getViewer() != null;
                        getViewer().getUserInfoSuccess(anchorUserInfoBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        super.onError(apiException);

                    }
                });
    }


    public void attent(String anchor_id,int type,AnchorUserInfoBean anchorUserInfoBean) {
        XHttp.post(ApiServices.ATTENTCLICK)
                .accessToken(true)
                .params("anchor_id", anchor_id)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().attentSuccess(type,anchorUserInfoBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        super.onError(apiException);

                    }
                });
    }
}