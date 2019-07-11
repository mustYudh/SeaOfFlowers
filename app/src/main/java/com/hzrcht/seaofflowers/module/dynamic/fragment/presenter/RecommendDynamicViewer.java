package com.hzrcht.seaofflowers.module.dynamic.fragment.presenter;

import com.hzrcht.seaofflowers.module.dynamic.bean.MineDynamicBean;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineLocationDynamicBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.ReviewListBean;
import com.yu.common.mvp.Viewer;


public interface RecommendDynamicViewer extends Viewer {
    void getStateListSuccess(MineDynamicBean mineDynamicBean);

    void stateLikeSuccess(MineLocationDynamicBean item);

    void getReviewListSuccess(ReviewListBean reviewListBean, MineLocationDynamicBean item, String state_id);

    void stateReviewSuccess(MineLocationDynamicBean item);
}
