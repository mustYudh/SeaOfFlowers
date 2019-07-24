package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.SysWeChatBean;
import com.yu.common.mvp.Viewer;


public interface InAuthenticationViewer extends Viewer {
    void getSysWechatSuccess(SysWeChatBean sysWeChatBean);
}
