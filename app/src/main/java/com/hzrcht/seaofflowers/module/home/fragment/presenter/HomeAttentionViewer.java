package com.hzrcht.seaofflowers.module.home.fragment.presenter;

import com.hzrcht.seaofflowers.module.home.bean.HomeAttentionBean;
import com.yu.common.mvp.Viewer;


public interface HomeAttentionViewer extends Viewer {
    void getAttentionListSuccess(HomeAttentionBean homeAttentionBean);

    void getAttentionListFail();
}
