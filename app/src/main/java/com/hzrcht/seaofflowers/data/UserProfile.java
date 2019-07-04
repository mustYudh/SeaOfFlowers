package com.hzrcht.seaofflowers.data;

import android.content.Context;
import android.text.TextUtils;

import com.hzrcht.seaofflowers.APP;

import java.io.Serializable;

public class UserProfile implements Serializable {

    private static UserProfile instance;

    private static final String SHARE_PREFERENCES_NAME = ".public_profile";
    private static final String TOKEN = "token";
    private static final String ANCHORTYPE = "anchorType";

    private SharedPreferencesHelper spHelper;

    private UserProfile() {
        spHelper = SharedPreferencesHelper.create(
                APP.getInstance().getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE));
    }

    public synchronized static UserProfile getInstance() {
        if (instance == null) {
            synchronized (UserProfile.class) {
                if (instance == null) {
                    instance = new UserProfile();
                }
            }
        }
        return instance;
    }

    public void setToken(String token) {
        spHelper.putString(TOKEN, token);
    }

    public String getAppToken() {
        return spHelper.getString(TOKEN, "");
    }

    public void setAnchorType(int type) {
        spHelper.putInt(ANCHORTYPE, type);
    }

    public int getAnchorType() {
        return spHelper.getInt(ANCHORTYPE, 0);
    }


    public boolean isAppLogin() {
        return !TextUtils.isEmpty(getAppToken());
    }

    /**
     * 退出登录
     */
    public void clean() {
        spHelper.clear();
    }

}
