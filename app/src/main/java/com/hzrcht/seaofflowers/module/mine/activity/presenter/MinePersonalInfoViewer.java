package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.bean.PayInfo;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineDynamicBean;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineLocationDynamicBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.AnchorUserInfoBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.PhotoAlbumBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.ReviewListBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserIsAnchorBean;
import com.hzrcht.seaofflowers.module.mine.bean.SysMoneyBean;
import com.yu.common.mvp.Viewer;


public interface MinePersonalInfoViewer extends Viewer {
    void attentSuccess(int type, AnchorUserInfoBean anchorUserInfoBean);

    void getUserInfoSuccess(AnchorUserInfoBean anchorUserInfoBean);

    void getStateListSuccess(MineDynamicBean mineDynamicBean);

    void stateLikeSuccess(MineLocationDynamicBean item);

    void stateDelSuccess(int position);

    void getReviewListSuccess(ReviewListBean reviewListBean, MineLocationDynamicBean item, String state_id);

    void stateReviewSuccess(MineLocationDynamicBean item);

    void getPhotoAlbumSuccess(PhotoAlbumBean photoAlbumBean);

    void lookPhoneSuccess(AnchorUserInfoBean anchorUserInfoBean);

    void lookPhoneFail(int code,String msg);

    void getSysMoneySuccess(SysMoneyBean sysMoneyBean);

    void orderAddSuccess(PayInfo payInfo);

    void getIsAnchorSuccess(UserIsAnchorBean userIsAnchorBean);
}
