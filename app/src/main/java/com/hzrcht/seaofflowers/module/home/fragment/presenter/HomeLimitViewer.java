package com.hzrcht.seaofflowers.module.home.fragment.presenter;

import com.hzrcht.seaofflowers.module.home.bean.HomeAnchorListBean;
import com.hzrcht.seaofflowers.module.home.bean.HomeBannerBean;
import com.yu.common.mvp.Viewer;


public interface HomeLimitViewer extends Viewer {
    void getAnchorListSuccess(HomeAnchorListBean homeAnchorListBean);

    void getAnchorListFail();

    void getBannerListSuccess(HomeBannerBean homeBannerBean);

}
