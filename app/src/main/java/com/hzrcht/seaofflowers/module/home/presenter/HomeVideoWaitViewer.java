package com.hzrcht.seaofflowers.module.home.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.UserIsAnchorBean;
import com.yu.common.mvp.Viewer;


public interface HomeVideoWaitViewer extends Viewer {
    void getIsAnchorSuccess(UserIsAnchorBean userIsAnchorBean);
}
