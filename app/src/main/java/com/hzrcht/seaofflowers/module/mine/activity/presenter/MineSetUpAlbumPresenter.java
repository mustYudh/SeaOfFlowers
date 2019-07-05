package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UploadImgBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

import java.io.File;


public class MineSetUpAlbumPresenter extends BaseViewPresenter<MineSetUpAlbumViewer> {

    public MineSetUpAlbumPresenter(MineSetUpAlbumViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void uploadImg(File file) {
        XHttp.post("http://huahai.hzrcht.com/api/upload/index")
                .params("uptype", "oss")
                .params("file", file)
                .accessToken(true)
                .execute(UploadImgBean.class)
                .subscribeWith(new TipRequestSubscriber<UploadImgBean>() {
                    @Override
                    protected void onSuccess(UploadImgBean uploadImgBean) {
                        assert getViewer() != null;
                        ToastUtils.show("发送成功");
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        super.onError(apiException);

                    }
                });
    }

    @SuppressLint("CheckResult")
    public void addImg(String url) {
        XHttp.post("http://huahai.hzrcht.com/api/img/add")
                .params("url", url)
                .accessToken(true)
                .execute(UploadImgBean.class)
                .subscribeWith(new TipRequestSubscriber<UploadImgBean>() {
                    @Override
                    protected void onSuccess(UploadImgBean uploadImgBean) {
                        assert getViewer() != null;
                        ToastUtils.show("上传成功");
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        super.onError(apiException);

                    }
                });
    }
}