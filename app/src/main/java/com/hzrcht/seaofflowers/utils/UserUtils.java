package com.hzrcht.seaofflowers.utils;

import android.content.Intent;
import android.text.TextUtils;
import com.hzrcht.seaofflowers.APP;
import com.hzrcht.seaofflowers.data.UserProfile;
import com.hzrcht.seaofflowers.module.login.activity.LoginActivity;
import com.yu.common.launche.LauncherHelper;

/**
 * @author yudenghao
 */

public class UserUtils {

  public static boolean isLogin() {
    return !TextUtils.isEmpty(UserProfile.getInstance().getAppToken());
  }
  public static void reLogin() {
    if (!LoginActivity.pFlag) {
      LoginActivity.pFlag = true;
      UserProfile.getInstance().clean();
      LauncherHelper.from(APP.getInstance())
          .addFlag(Intent.FLAG_ACTIVITY_CLEAR_TOP)
          .startActivity(LoginActivity.class);
    }
  }
}
