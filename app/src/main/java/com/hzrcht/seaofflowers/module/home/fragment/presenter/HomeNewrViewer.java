package com.hzrcht.seaofflowers.module.home.fragment.presenter;

import com.hzrcht.seaofflowers.module.home.bean.HomeAnchorListBean;
import com.yu.common.mvp.Viewer;


public interface HomeNewrViewer extends Viewer {
    void getAnchorListSuccess(HomeAnchorListBean homeAnchorListBean);
}
