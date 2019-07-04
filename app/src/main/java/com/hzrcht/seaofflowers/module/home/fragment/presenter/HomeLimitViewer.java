package com.hzrcht.seaofflowers.module.home.fragment.presenter;

import com.hzrcht.seaofflowers.module.home.bean.AnchorListBean;
import com.hzrcht.seaofflowers.module.home.bean.HomeBannerBean;
import com.yu.common.mvp.Viewer;


public interface HomeLimitViewer extends Viewer {
    void getAnchorListSuccess(AnchorListBean anchorListBean);

    void getBannerListSuccess(HomeBannerBean homeBannerBean);

}
