package com.hzrcht.seaofflowers.module.message.activity.presenter;

import com.hzrcht.seaofflowers.module.message.bean.UserSysMessageBean;
import com.yu.common.mvp.Viewer;


public interface SystemMessageViewer extends Viewer {
    void getUserSysMessageSuccess(UserSysMessageBean userSysMessageBean);

    void getUserSysMessageFail();
}
