package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.PhotoAlbumBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;


public class MinePhotoAlbumPresenter extends BaseViewPresenter<MinePhotoAlbumViewer> {

    public MinePhotoAlbumPresenter(MinePhotoAlbumViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void getPhotoAlbum(int page, int pageSize) {
        XHttp.post(ApiServices.GETPHOTOALBUM)
                .params("page", page + "")
                .params("pageSize", pageSize + "")
                .accessToken(true)
                .execute(PhotoAlbumBean.class)
                .subscribeWith(new TipRequestSubscriber<PhotoAlbumBean>() {
                    @Override
                    protected void onSuccess(PhotoAlbumBean photoAlbumBean) {
                        assert getViewer() != null;
                        getViewer().getPhotoAlbumSuccess(photoAlbumBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        super.onError(apiException);
                    }
                });
    }
}