package com.hzrcht.seaofflowers.module.dynamic.fragment.presenter;

import com.hzrcht.seaofflowers.module.dynamic.bean.MineDynamicBean;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineLocationDynamicBean;
import com.yu.common.mvp.Viewer;


public interface DynamicViewer extends Viewer {
    void getStateListSuccess(MineDynamicBean mineDynamicBean);

    void stateLikeSuccess(MineLocationDynamicBean item);
}
