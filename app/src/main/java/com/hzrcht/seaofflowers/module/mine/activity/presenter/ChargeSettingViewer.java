package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.UserChargeBean;
import com.yu.common.mvp.Viewer;


public interface ChargeSettingViewer extends Viewer {
    void getUserChargeSuccess(UserChargeBean userChargeBean);

    void userEditChargeSuccess();
}
