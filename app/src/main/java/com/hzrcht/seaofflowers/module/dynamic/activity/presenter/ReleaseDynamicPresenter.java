package com.hzrcht.seaofflowers.module.dynamic.activity.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UploadImgBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.lifecycle.RxLifecycle;
import com.xuexiang.xhttp2.request.PostRequest;
import com.xuexiang.xhttp2.subsciber.ProgressLoadingSubscriber;
import com.yu.common.framework.BaseViewPresenter;

import java.io.File;

@SuppressLint("CheckResult")
public class ReleaseDynamicPresenter extends BaseViewPresenter<ReleaseDynamicViewer> {

    public ReleaseDynamicPresenter(ReleaseDynamicViewer viewer) {
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

    public void stateAdd(String img_url, String title, String is_video) {
        XHttp.post(ApiServices.STATEADD).accessToken(true)
                .params("url", img_url)
                .params("title", title)
                .params("is_video", is_video)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().stateAddSuccess();
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().stateAddFail();
                    }
                });
    }
}