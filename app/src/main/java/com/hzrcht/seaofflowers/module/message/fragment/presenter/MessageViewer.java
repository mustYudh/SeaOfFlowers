package com.hzrcht.seaofflowers.module.message.fragment.presenter;

import com.hzrcht.seaofflowers.module.mine.activity.bean.UserChargeBean;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;
import com.yu.common.mvp.Viewer;


public interface MessageViewer extends Viewer {
    void getUserChargeSuccess(UserChargeBean userChargeBean, ConversationInfo messageInfo);
}
