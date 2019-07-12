package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.SysLabelBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserPhotoListBean;
import com.yu.common.mvp.Viewer;

import java.util.List;

public interface MineRedactDataViewer extends Viewer {
    void getPhotoList(List<UserPhotoListBean> list);

    void getSysLabelSuccess(SysLabelBean sysLabelBean);
}
