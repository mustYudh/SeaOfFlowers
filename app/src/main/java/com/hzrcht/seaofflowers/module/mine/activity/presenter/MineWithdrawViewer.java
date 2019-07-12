package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.UserAccountsBean;
import com.hzrcht.seaofflowers.module.mine.bean.SysMoneyBean;
import com.hzrcht.seaofflowers.module.mine.bean.UserAmountBean;
import com.yu.common.mvp.Viewer;


public interface MineWithdrawViewer extends Viewer {
    void getUserAccountsSuccess(UserAccountsBean userAccountsBean);

    void getSysMoneySuccess(SysMoneyBean sysMoneyBean);

    void userWithdrawSuccess();

    void getUserAmountSuccess(UserAmountBean amountBean);


}
