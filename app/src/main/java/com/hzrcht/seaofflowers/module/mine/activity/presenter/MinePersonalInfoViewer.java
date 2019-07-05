package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.AnchorUserInfoBean;
import com.yu.common.mvp.Viewer;


public interface MinePersonalInfoViewer extends Viewer {
    void attentSuccess(int type,AnchorUserInfoBean anchorUserInfoBean);

    void getUserInfoSuccess(AnchorUserInfoBean anchorUserInfoBean);
}
