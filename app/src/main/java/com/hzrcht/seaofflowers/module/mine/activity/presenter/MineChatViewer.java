package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.SysGiftBean;
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.yu.common.mvp.Viewer;


public interface MineChatViewer extends Viewer {
    void getSysGiftSuccess(SysGiftBean sysGiftBean, ChatLayout mChatLayout);

    void sendGiftSuccess(ChatLayout mChatLayout,SysGiftBean.ResultBean resultBean);
}
