package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.ReviewListBean;
import com.hzrcht.seaofflowers.module.mine.bean.MineLocationUserDynamicBean;
import com.hzrcht.seaofflowers.module.mine.bean.MineUserDynamicBean;
import com.yu.common.mvp.Viewer;


public interface MineDynamicViewer extends Viewer {
    void getStateListSuccess(MineUserDynamicBean mineUserDynamicBean);

    void getStateListFail();

    void stateLikeSuccess(MineLocationUserDynamicBean item);

    void stateDelSuccess(int position);

    void getReviewListSuccess(ReviewListBean reviewListBean,MineLocationUserDynamicBean item,String state_id);

    void stateReviewSuccess(MineLocationUserDynamicBean item);
}
