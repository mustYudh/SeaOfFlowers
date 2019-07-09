package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.dynamic.bean.MineDynamicBean;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineLocationDynamicBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.AnchorUserInfoBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.ReviewListBean;
import com.yu.common.mvp.Viewer;


public interface MinePersonalInfoViewer extends Viewer {
    void attentSuccess(int type, AnchorUserInfoBean anchorUserInfoBean);

    void getUserInfoSuccess(AnchorUserInfoBean anchorUserInfoBean);

    void getStateListSuccess(MineDynamicBean mineDynamicBean);

    void stateLikeSuccess(MineLocationDynamicBean item);

    void stateDelSuccess(int position);

    void getReviewListSuccess(ReviewListBean reviewListBean, MineLocationDynamicBean item, String state_id);

    void stateReviewSuccess(MineLocationDynamicBean item);
}
