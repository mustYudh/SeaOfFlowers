package com.hzrcht.seaofflowers.module.login.activity.presenter;

import com.hzrcht.seaofflowers.module.login.bean.LoginBean;
import com.yu.common.mvp.Viewer;

/**
 * @author yudneghao
 * @date 2019-06-03
 */
public interface SelectLoginViewer extends Viewer {
    void getWxInfoSuccess(LoginBean loginBean);
}
