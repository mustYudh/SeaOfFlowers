package com.hzrcht.seaofflowers.module.login.activity.presenter;

import com.hzrcht.seaofflowers.module.login.bean.WxConfigBean;
import com.yu.common.mvp.Viewer;

/**
 * @author yudneghao
 * @date 2019-06-03
 */
public interface SplashViewer extends Viewer {
    void getWxConfigSuccess(WxConfigBean wxConfigBean);
    void getWxConfigFail();
}
