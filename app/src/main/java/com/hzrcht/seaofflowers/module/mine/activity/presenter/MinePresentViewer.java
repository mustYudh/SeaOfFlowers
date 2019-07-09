package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.GiftListBean;
import com.yu.common.mvp.Viewer;


public interface MinePresentViewer extends Viewer {
    void getGiftIndexSuccess(GiftListBean giftListBean);
}
