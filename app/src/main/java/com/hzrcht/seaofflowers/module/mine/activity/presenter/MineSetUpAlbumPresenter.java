package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;
import android.util.Log;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UploadImgBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.lifecycle.RxLifecycle;
import com.xuexiang.xhttp2.subsciber.ProgressLoadingSubscriber;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;
import java.io.File;

@SuppressLint("CheckResult")
public class MineSetUpAlbumPresenter extends BaseViewPresenter<MineSetUpAlbumViewer> {

    public MineSetUpAlbumPresenter(MineSetUpAlbumViewer viewer) {
        super(viewer);
    }


    public void uploadImg(File file) {
      XHttp.post("/api/upload/index")
          .params("uptype", "oss")
          .uploadFile("file", file, (bytesWritten, contentLength, done) -> {

          }).execute(Boolean.class)
          .compose(RxLifecycle.with(getActivity()).bindToLifecycle())
          .subscribeWith(new ProgressLoadingSubscriber<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
              Log.e("======>","上传成功");
            }
          });
    }


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