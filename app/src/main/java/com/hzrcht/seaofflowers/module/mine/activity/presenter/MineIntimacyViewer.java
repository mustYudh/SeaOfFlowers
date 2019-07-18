package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.AnchorUserInfoBean;
import com.yu.common.mvp.Viewer;


public interface MineIntimacyViewer extends Viewer {
    void getUserInfoSuccess(AnchorUserInfoBean anchorUserInfoBean);
}
