package com.hzrcht.seaofflowers.data;

import android.content.Context;
import android.text.TextUtils;

import com.hzrcht.seaofflowers.APP;
import com.hzrcht.seaofflowers.module.login.bean.LoginBean;
import com.tencent.imsdk.TIMManager;

import java.io.Serializable;

public class UserProfile implements Serializable {

    private static UserProfile instance;

    private static final String SHARE_PREFERENCES_NAME = ".public_profile";
    private static final String APPTOKEN = "token";
    private static final String APP_USER_ID = "account";
    private static final String USERSEX = "user_sex";
    private static final String USERNAME = "user_name";
    private static final String ANCHORTYPE = "anchor_type";
    private static final String USERSIG = "user_sig";
    private static final String APPLOGIN = "app_login";

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

    public void appLogin(LoginBean userInfo) {
        setAppAccount(userInfo.info.id);
        setUserSex(userInfo.info.sex);
        setUserName(userInfo.info.nick_name);
        setToken(userInfo.token);
    }


    public void setToken(String token) {
        spHelper.putString(APPTOKEN, token);
    }

    public String getAppToken() {
        return spHelper.getString(APPTOKEN, "");
    }

    public void setUserSig(String userSig) {
        spHelper.putString(USERSIG, userSig);
    }

    public String getUserSig() {
        return spHelper.getString(USERSIG, "");
    }

    public int getUserId() {
        return spHelper.getInt(APP_USER_ID, -1);
    }

    private void setAppAccount(int account) {
        spHelper.putInt(APP_USER_ID, account);
    }


    public void setUserSex(int user_sex) {
        spHelper.putInt(USERSEX, user_sex);
    }

    public int getUserSex() {
        return spHelper.getInt(USERSEX, 0);
    }

    public void setUserName(String user_name) {
        spHelper.putString(USERNAME, user_name);
    }

    public String getUserName() {
        return spHelper.getString(USERNAME, "");
    }

    public void setAnchorType(int type) {
        spHelper.putInt(ANCHORTYPE, type);
    }

    public int getAnchorType() {
        return spHelper.getInt(ANCHORTYPE, 0);
    }

    public void setApplogin(boolean applogin) {
        spHelper.putBoolean(APPLOGIN, applogin);
    }

    public boolean getApplogin() {
        return spHelper.getBoolean(APPLOGIN, false);
    }

    public boolean isAppLogin() {
        return !TextUtils.isEmpty(getAppToken()) && getUserSex() != 0 && !TextUtils.isEmpty(TIMManager.getInstance().getLoginUser());
    }

    /**
     * 退出登录
     */
    public void clean() {
        spHelper.clear();
    }

}
