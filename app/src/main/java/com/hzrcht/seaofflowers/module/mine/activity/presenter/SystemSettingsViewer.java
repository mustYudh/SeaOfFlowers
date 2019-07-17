package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.UserConfigBean;
import com.yu.common.mvp.Viewer;


public interface SystemSettingsViewer extends Viewer {
    void getUserConfigSuccess(UserConfigBean userConfigBean);

    void userEditConfigSuccess(UserConfigBean userConfigBean,String type);
}
