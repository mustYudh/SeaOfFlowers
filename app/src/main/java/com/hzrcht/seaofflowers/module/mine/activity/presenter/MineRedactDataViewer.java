package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.SysLabelBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UploadImgBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserDetailBean;
import com.yu.common.mvp.Viewer;

public interface MineRedactDataViewer extends Viewer {
    void getSysLabelSuccess(SysLabelBean sysLabelBean);

    void getUserDetailSuccess(UserDetailBean userDetailBean);

    void setUserDetailSuccess();

    void uploadImgSuccess(UploadImgBean uploadImgBean);

    void uploadCoverSuccess(UploadImgBean uploadImgBean);

    void uploadImgFail();

    void uploadCoverFail();
}
