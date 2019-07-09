package com.hzrcht.seaofflowers.module.home.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.UploadImgBean;
import com.yu.common.mvp.Viewer;


public interface HomeReportViewer extends Viewer {
    void uploadImgSuccess(UploadImgBean uploadImgBean);

    void reportSuccess();

    void reportFail();
}
