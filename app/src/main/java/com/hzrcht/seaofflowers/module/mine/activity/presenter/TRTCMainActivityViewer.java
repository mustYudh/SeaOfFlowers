package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.home.bean.HomePayCoinBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.SysGiftBean;
import com.yu.common.mvp.Viewer;


public interface TRTCMainActivityViewer extends Viewer {
    void liveEndSuccess();

    void liveEndFail(String msg);

    void attentSuccess(String type);

    void livePayCoinSuccess(HomePayCoinBean homePayCoinBean);

    void livePayCoinFail(int code, String msg);

    void getSysGiftSuccess(SysGiftBean sysGiftBean);

    void sendGiftSuccess(SysGiftBean.ResultBean resultBean);
}
