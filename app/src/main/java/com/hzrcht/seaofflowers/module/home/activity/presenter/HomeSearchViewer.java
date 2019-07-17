package com.hzrcht.seaofflowers.module.home.activity.presenter;

import com.hzrcht.seaofflowers.module.home.bean.HomeSearchBean;
import com.yu.common.mvp.Viewer;


public interface HomeSearchViewer extends Viewer {
    void getUserSearchSuccess(HomeSearchBean homeSearchBean);

    void getUserSearchFail(String msg);
}
