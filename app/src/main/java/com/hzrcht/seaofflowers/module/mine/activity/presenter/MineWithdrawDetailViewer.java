package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.UserBillListBean;
import com.yu.common.mvp.Viewer;


public interface MineWithdrawDetailViewer extends Viewer {
    void getUserBillSuccess(UserBillListBean userBillListBean);

    void getUserBillFail();
}
