package com.hzrcht.seaofflowers.module.home.presenter;

import com.hzrcht.seaofflowers.module.home.bean.HomePayCoinBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserIsAnchorBean;
import com.yu.common.mvp.Viewer;


public interface HomeVideoWaitViewer extends Viewer {
    void getIsAnchorSuccess(UserIsAnchorBean userIsAnchorBean);

    void livePayCoinSuccess(HomePayCoinBean homePayCoinBean,String[] split);

    void livePayCoinFail(int code, String msg,String[] split);

    void liveEndSuccess(String[] split,int code);

    void liveEndFail(String msg,int code);
}
