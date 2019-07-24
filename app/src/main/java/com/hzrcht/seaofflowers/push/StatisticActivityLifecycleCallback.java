package com.hzrcht.seaofflowers.push;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.tencent.imsdk.TIMBackgroundParam;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.ext.message.TIMConversationExt;
import com.tencent.imsdk.ext.message.TIMManagerExt;

import java.util.List;

public class StatisticActivityLifecycleCallback implements Application.ActivityLifecycleCallbacks {
    private int foregroundActivities = 0;
    private boolean isChangingConfiguration;
    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        foregroundActivities++;
        if (foregroundActivities == 1 && !isChangingConfiguration) {
            // 应用切到前台
            TIMManager.getInstance().doForeground(new TIMCallBack() {
                @Override
                public void onError(int code, String desc) {
                }

                @Override
                public void onSuccess() {
                }
            });
        }
        isChangingConfiguration = false;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        foregroundActivities--;
        if (foregroundActivities == 0) {
            // 应用切到后台
            int unReadCount = 0;
            List<TIMConversation> conversationList = TIMManagerExt.getInstance().getConversationList();
            for (TIMConversation timConversation : conversationList) {
                TIMConversationExt timConversationExt = new TIMConversationExt(timConversation);
                unReadCount += timConversationExt.getUnreadMessageNum();
            }
            TIMBackgroundParam param = new TIMBackgroundParam();
            param.setC2cUnread(unReadCount);
            TIMManager.getInstance().doBackground(param, new TIMCallBack() {
                @Override
                public void onError(int code, String desc) {
                }

                @Override
                public void onSuccess() {
                }
            });
        }
        isChangingConfiguration = activity.isChangingConfigurations();
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
