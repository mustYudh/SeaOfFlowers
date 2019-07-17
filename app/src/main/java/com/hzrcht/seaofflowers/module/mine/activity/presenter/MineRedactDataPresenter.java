package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import android.annotation.SuppressLint;

import com.hzrcht.seaofflowers.bean.NoDataBean;
import com.hzrcht.seaofflowers.data.UserProfile;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.mine.activity.bean.SysLabelBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UploadImgBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserDetailBean;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.lifecycle.RxLifecycle;
import com.xuexiang.xhttp2.subsciber.ProgressLoadingSubscriber;
import com.yu.common.framework.BaseViewPresenter;

import java.io.File;

@SuppressLint("CheckResult")
public class MineRedactDataPresenter extends BaseViewPresenter<MineRedactDataViewer> {

    public MineRedactDataPresenter(MineRedactDataViewer viewer) {
        super(viewer);
    }


    public void getSysLabel() {
        XHttp.post(ApiServices.SYSLABEL)
                .accessToken(true)
                .params("sex", UserProfile.getInstance().getUserSex() + "")
                .execute(SysLabelBean.class)
                .subscribeWith(new TipRequestSubscriber<SysLabelBean>() {
                    @Override
                    protected void onSuccess(SysLabelBean sysLabelBean) {
                        assert getViewer() != null;
                        getViewer().getSysLabelSuccess(sysLabelBean);
                    }
                });
    }


    public void getUserDetail() {
        XHttp.post(ApiServices.USERDETAIL)
                .accessToken(true)
                .execute(UserDetailBean.class)
                .subscribeWith(new TipRequestSubscriber<UserDetailBean>() {
                    @Override
                    protected void onSuccess(UserDetailBean userDetailBean) {
                        assert getViewer() != null;
                        getViewer().getUserDetailSuccess(userDetailBean);
                    }
                });
    }


    public void setUserDetail(String cover, String head_img, String nick_name, String work, String phone, String age, String kg, String star, String city, String sign, String hight, String lable) {
        XHttp.post(ApiServices.USEREDIT)
                .params("cover", cover)
                .params("head_img", head_img)
                .params("nick_name", nick_name)
                .params("work", work)
                .params("phone", phone)
                .params("age", age)
                .params("kg", kg)
                .params("star", star)
                .params("city", city)
                .params("sign", sign)
                .params("hight", hight)
                .params("lable", lable)

                .accessToken(true)
                .execute(NoDataBean.class)
                .subscribeWith(new TipRequestSubscriber<NoDataBean>() {
                    @Override
                    protected void onSuccess(NoDataBean noDataBean) {
                        assert getViewer() != null;
                        getViewer().setUserDetailSuccess();
                    }
                });
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

    public void uploadCover(File file) {
        XHttp.post(ApiServices.UPLOADIMG)
                .params("uptype", "oss")
                .uploadFile("file", file, (bytesWritten, contentLength, done) -> {

                }).execute(UploadImgBean.class)
                .compose(RxLifecycle.with(getActivity()).bindToLifecycle())
                .subscribeWith(new ProgressLoadingSubscriber<UploadImgBean>() {
                    @Override
                    public void onSuccess(UploadImgBean uploadImgBean) {

                        assert getViewer() != null;
                        getViewer().uploadCoverSuccess(uploadImgBean);
                    }

                    @Override
                    public void onError(ApiException e) {
                        assert getViewer() != null;
                        getViewer().uploadCoverFail();
                    }
                });
    }

}