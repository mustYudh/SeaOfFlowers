package com.hzrcht.seaofflowers.module.dynamic.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.UploadImgBean;
import com.yu.common.mvp.Viewer;


public interface ReleaseDynamicViewer extends Viewer {
    void uploadImgSuccess(UploadImgBean uploadImgBean);

    void stateAddSuccess();

    void stateAddFail();
}
