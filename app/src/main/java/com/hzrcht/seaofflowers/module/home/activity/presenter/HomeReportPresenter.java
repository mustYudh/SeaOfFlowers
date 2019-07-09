package com.hzrcht.seaofflowers.module.home.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UploadImgBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.lifecycle.RxLifecycle;
import com.xuexiang.xhttp2.subsciber.ProgressLoadingSubscriber;
import com.yu.common.framework.BaseViewPresenter;

import java.io.File;

@SuppressLint("CheckResult")
public class HomeReportPresenter extends BaseViewPresenter<HomeReportViewer> {

    public HomeReportPresenter(HomeReportViewer viewer) {
        super(viewer);
    }

    public void uploadImg(File file) {
        XHttp.post(ApiServices.UPLOADIMG)
                .params("uptype", "oss")
                .uploadFile("file", file, (bytesWritten, contentLength, done) -> {

                }).execute(UploadImgBean.class)
                .compose(RxLifecycle.with(getActivity()).bindToLifecycle())
                .subscribeWith(new ProgressLoadingSubscriber<UploadImgBean>() {
                    @Override
                    public void onSuccess(UploadImgBean uploadImgBean) {

                        assert getViewer() != null;
                        getViewer().uploadImgSuccess(uploadImgBean);
                    }
                });
    }


    public void report(String anchor_id, String state_id, String img_url, String title) {
        XHttp.post(ApiServices.STATEREPORT)
                .accessToken(true)
                .params("anchor_id", anchor_id)
                .params("img_url", img_url)
                .params("state_id", state_id)
                .params("title", title)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().reportSuccess();
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().reportFail();
                    }
                });
    }
}