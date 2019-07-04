package com.hzrcht.seaofflowers.module.login.activity.presenter;

import com.hzrcht.seaofflowers.module.login.bean.LoginBean;
import com.yu.common.login.LoginRedirectInterface;

public interface LoginViewer extends LoginRedirectInterface {
    void loginSuccess(LoginBean loginBean);
}
