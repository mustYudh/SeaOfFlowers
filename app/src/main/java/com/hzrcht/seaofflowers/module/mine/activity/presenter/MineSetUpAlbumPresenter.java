package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

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
public class MineSetUpAlbumPresenter extends BaseViewPresenter<MineSetUpAlbumViewer> {

    public MineSetUpAlbumPresenter(MineSetUpAlbumViewer viewer) {
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

                    @Override
                    public void onError(ApiException e) {
                        assert getViewer() != null;
                        getViewer().uploadImgFail();
                    }
                });
    }


    public void addAlbum(String url) {
        XHttp.post(ApiServices.ADDALBUM)
                .params("url", url)
                .params("is_video", "0")
                .accessToken(true)
                .execute(UploadImgBean.class)
                .subscribeWith(new TipRequestSubscriber<UploadImgBean>() {
                    @Override
                    protected void onSuccess(UploadImgBean uploadImgBean) {
                        assert getViewer() != null;
                        getViewer().addAlbumSuccess();
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().addAlbumFail();
                    }
                });
    }
}