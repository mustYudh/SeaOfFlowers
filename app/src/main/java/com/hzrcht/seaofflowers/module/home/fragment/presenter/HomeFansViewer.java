package com.hzrcht.seaofflowers.module.home.fragment.presenter;

import com.hzrcht.seaofflowers.module.home.bean.HomeFansBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.LiveStartBean;
import com.yu.common.mvp.Viewer;


public interface HomeFansViewer extends Viewer {
    void getFansListSuccess(HomeFansBean homeFansBean);

    void getFansListFail();

    void liveStartSuccess(LiveStartBean liveStartBean,HomeFansBean.RowBean item);
}
