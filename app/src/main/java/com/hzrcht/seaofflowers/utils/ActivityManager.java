package com.hzrcht.seaofflowers.utils;

import android.content.Intent;

import com.hzrcht.seaofflowers.APP;
import com.hzrcht.seaofflowers.base.BaseActivity;
import com.hzrcht.seaofflowers.data.UserProfile;
import com.hzrcht.seaofflowers.module.login.activity.SelectLoginActivity;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.yu.common.launche.LauncherHelper;

import java.util.HashSet;

public class ActivityManager {

    private static HashSet<BaseActivity> hashSet = new HashSet<>();

    private static final ActivityManager ourInstance = new ActivityManager();

    public static ActivityManager getInstance() {
        return ourInstance;
    }

    private ActivityManager() {
    }

    //这就是存在内存中
    public void addActivity(BaseActivity activity) {
        try {
            if (activity != null && !activity.isDestroyed()) {
                hashSet.add(activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void exit() {
        try {
            for (BaseActivity activity : hashSet) {
                if (activity != null && !activity.isDestroyed())
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void reLogin() {
        TIMManager.getInstance().logout(new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                appLogout();
            }

            @Override
            public void onSuccess() {
                appLogout();
            }
        });
    }

    private void appLogout() {
        UserProfile.getInstance().clean();
        Intent intent = new Intent(APP.getInstance(), SelectLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        LauncherHelper.from(APP.getInstance()).startActivity(intent);
        exit();
    }
}
