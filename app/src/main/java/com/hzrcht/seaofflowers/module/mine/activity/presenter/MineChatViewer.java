package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.LiveStartBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.SysGiftBean;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserIsAnchorBean;
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.yu.common.mvp.Viewer;


public interface MineChatViewer extends Viewer {
    void getSysGiftSuccess(SysGiftBean sysGiftBean, ChatLayout mChatLayout);

    void getIsAnchorSuccess(UserIsAnchorBean userIsAnchorBean);

    void chatStartSuccess();

    void sendGiftSuccess(ChatLayout mChatLayout,SysGiftBean.ResultBean resultBean);

    void liveStartSuccess(LiveStartBean liveStartBean,UserIsAnchorBean userIsAnchorBean);
}
