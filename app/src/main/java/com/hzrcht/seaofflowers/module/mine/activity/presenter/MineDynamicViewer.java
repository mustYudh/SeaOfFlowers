package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.dynamic.bean.MineDynamicBean;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineLocationDynamicBean;
import com.yu.common.mvp.Viewer;


public interface MineDynamicViewer extends Viewer {
    void getStateListSuccess(MineDynamicBean mineDynamicBean);

    void stateLikeSuccess(MineLocationDynamicBean item);

    void stateDelSuccess(int position);
}
