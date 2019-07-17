package com.hzrcht.seaofflowers.module.mine.fragment.presenter;

import com.hzrcht.seaofflowers.module.mine.bean.MineUserInfoBean;
import com.yu.common.mvp.Viewer;


public interface MineViewer extends Viewer {
    void userInfoSuccess(MineUserInfoBean mineUserInfoBean);

    void userEditConfigSuccess(MineUserInfoBean mineUserInfoBean);
}
