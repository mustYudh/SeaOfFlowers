package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.home.bean.HomeAttentionBean;
import com.yu.common.mvp.Viewer;


public interface MineAttentionViewer extends Viewer {
    void getAttentionListSuccess(HomeAttentionBean homeAttentionBean);

    void getAttentionListFail();
}
