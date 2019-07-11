package com.hzrcht.seaofflowers.module.mine.fragment.presenter;

import com.hzrcht.seaofflowers.bean.PayInfo;
import com.hzrcht.seaofflowers.module.mine.bean.SysMoneyBean;
import com.hzrcht.seaofflowers.module.mine.bean.UserAmountBean;
import com.yu.common.mvp.Viewer;


public interface RechargeCoinViewer extends Viewer {
    void getSysMoneySuccess(SysMoneyBean sysMoneyBean);

    void getUserAmountSuccess(UserAmountBean amountBean);

    void orderAddSuccess(PayInfo payInfo);

}
