package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.NoTipRequestSubscriber;
import com.xuexiang.xhttp2.XHttp;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class ViewBigImagePresenter extends BaseViewPresenter<ViewBigImageViewer> {

    public ViewBigImagePresenter(ViewBigImageViewer viewer) {
        super(viewer);
    }

    public void imgDel(String id) {
        XHttp.post(ApiServices.IMGDEL)
                .accessToken(true)
                .params("id", id)
                .execute(NoDataBean.class)
                .subscribeWith(new NoTipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().imgDelSuccess();
                    }
                });
    }
}