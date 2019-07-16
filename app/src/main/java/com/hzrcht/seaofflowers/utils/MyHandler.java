package com.hzrcht.seaofflowers.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author : thb
 *     e-mail : xxx@xx
 *     time   : 2018/09/05
 *     desc   :
 * </pre>
 */


public class MyHandler extends Handler {
    WeakReference<Activity> mActivityReference;

    public MyHandler(Activity activity) {
        mActivityReference = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        Activity handlerAct = mActivityReference.get();
        if (handlerAct != null) {
            //执行业务逻辑
        }
    }
}