package com.hzrcht.seaofflowers.module.mine.fragment.presenter;

import com.hzrcht.seaofflowers.bean.PayInfo;
import com.hzrcht.seaofflowers.module.mine.bean.SysMoneyBean;
import com.yu.common.mvp.Viewer;


public interface DredgeVipViewer extends Viewer {
    void getSysMoneySuccess(SysMoneyBean sysMoneyBean);

    void orderAddSuccess(PayInfo payInfo);
}
