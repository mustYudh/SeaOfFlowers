package com.hzrcht.seaofflowers.push;

import android.text.TextUtils;
import android.util.Log;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMOfflinePushToken;
import com.tencent.imsdk.log.QLog;
import com.tencent.imsdk.utils.IMFunc;

public class ThirdPushTokenMgr {
    private static final String TAG = "ThirdPushTokenMgr";

    private String mThirdPushToken;

    private boolean mIsTokenSet = false;
    private boolean mIsLogin = false;

    public static ThirdPushTokenMgr getInstance() {
        return ThirdPushTokenHolder.instance;
    }

    private static class ThirdPushTokenHolder {
        private static final ThirdPushTokenMgr instance = new ThirdPushTokenMgr();
    }

    public void setIsLogin(boolean isLogin) {
        mIsLogin = isLogin;
    }

    public String getThirdPushToken() {
        return mThirdPushToken;
    }

    public void setThirdPushToken(String mThirdPushToken) {
        this.mThirdPushToken = mThirdPushToken;  // regId 在此处传值，结合上文自定义 BroadcastReciever 类文档说明
    }

    public void setPushTokenToTIM() {
        if (mIsTokenSet) {
            QLog.i(TAG, "setPushTokenToTIM mIsTokenSet true, ignore");
            return;
        }
        String token = ThirdPushTokenMgr.getInstance().getThirdPushToken();
        if (TextUtils.isEmpty(token)) {
            QLog.i(TAG, "setPushTokenToTIM third token is empty");
            mIsTokenSet = false;
            return;
        }
        if (!mIsLogin) {
            QLog.i(TAG, "setPushTokenToTIM not login, ignore");
            return;
        }
        TIMOfflinePushToken param = null;
        if (IMFunc.isBrandXiaoMi()) {     // 判断厂商品牌，根据不同厂商选择不同的推送服务
            param = new TIMOfflinePushToken(ApiServices.XM_PUSH_BUZID, token);
        } else if (IMFunc.isBrandHuawei()) {
            param = new TIMOfflinePushToken(ApiServices.HW_PUSH_BUZID, token);
        } else if (IMFunc.isBrandMeizu()) {
            param = new TIMOfflinePushToken(ApiServices.MZ_PUSH_BUZID, token);
        } else if (IMFunc.isBrandOppo()) {
            param = new TIMOfflinePushToken(ApiServices.OPPO_PUSH_BUZID, token);
        } else if (IMFunc.isBrandVivo()) {
            param = new TIMOfflinePushToken(ApiServices.VIVO_PUSH_BUZID, token);
        } else {
            return;
        }
        TIMManager.getInstance().setOfflinePushToken(param, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                Log.d(TAG, "setOfflinePushToken err code = " + code);
            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "setOfflinePushToken success");
                mIsTokenSet = true;
            }
        });
    }
}
