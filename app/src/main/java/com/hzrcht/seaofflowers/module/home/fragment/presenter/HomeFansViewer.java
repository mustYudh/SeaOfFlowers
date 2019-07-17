package com.hzrcht.seaofflowers.module.home.fragment.presenter;

import com.hzrcht.seaofflowers.module.home.bean.HomeFansBean;
import com.yu.common.mvp.Viewer;


public interface HomeFansViewer extends Viewer {
    void getFansListSuccess(HomeFansBean homeFansBean);
}
