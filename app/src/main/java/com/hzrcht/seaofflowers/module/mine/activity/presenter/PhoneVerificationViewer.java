package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.yu.common.mvp.Viewer;


public interface PhoneVerificationViewer extends Viewer {
    void sendVerCodeSuccess();

    void bindUserPhoneSuccess();
}
