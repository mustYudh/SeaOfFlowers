package com.hzrcht.seaofflowers.module.message.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.bean.MineCallBean;
import com.yu.common.mvp.Viewer;


public interface MineCallViewer extends Viewer {
    void getLiveListSuccess(MineCallBean mineCallBean);

    void getLiveListFail();
}
