package com.hzrcht.seaofflowers.action;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.hzrcht.seaofflowers.APP;
import com.hzrcht.seaofflowers.module.event.HomeDataRefreshEvent;
import com.hzrcht.seaofflowers.module.login.activity.LoginActivity;
import com.hzrcht.seaofflowers.module.web.WebViewActivity;
import com.hzrcht.seaofflowers.utils.UserUtils;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.login.BundleHelper;
import com.yu.common.toast.ToastUtils;
import org.greenrobot.eventbus.EventBus;

/**
 * @author yudneghao
 * @date 2019-06-04
 */
public class BaseActionHelper {
  public final static String LINK_URL = "LINK_URL";
  private static Context iContext = null;

  private BaseActionHelper(Context context) {
    this.iContext = context;
  }

  public static BaseActionHelper with(Context context) {
    return new BaseActionHelper(context);
  }

  public Context getContext() {
    if (iContext == null) {
      return APP.getInstance();
    }
    if (iContext instanceof Activity && ((Activity) iContext).isFinishing()) {
      return APP.getInstance();
    }
    return iContext;
  }

  /**
   * 需要登录才执行action
   *
   * @return true 未登录情况直接跳转登录界面  false 已登录不需要再登录
   */
  private boolean doNeedLogin(String action) {
    if (!UserUtils.isLogin()) {
      LauncherHelper.from(getContext())
          .startActivity(
              LoginActivity.callRedirectOtherActionIntent(getContext(), BaseActionHelper.LINK_URL,
                  BundleHelper.create().putString(LINK_URL, action).get()));
      return true;
    }
    return false;
  }

  public void handleAction(String action) {
    handleAction(action, false);
  }

  public void handleAction(String action, boolean needLogin) {
    if (TextUtils.isEmpty(action)) {
      return;
    }
    if (needLogin && doNeedLogin(action)) {
      return;
    }
    if (action.startsWith("http://") || action.startsWith("https://")) {
      doActionURL(action);
    }
    if (ActionEnums.TEST_LOGIN.isMatch(action)) {
      ToastUtils.show("测试登陆后操作");
    }
    if (ActionEnums.HOME_CENTER_TAB_STATUS.isMatch(action)) {
      String showCenterTab = ActionParams.parse(action).get("show", "true");
      Log.e("====>",showCenterTab);
      HomeDataRefreshEvent event = new HomeDataRefreshEvent();
      event.showCenterTab = Boolean.parseBoolean(showCenterTab);
      EventBus.getDefault().post(event);
    }
  }

  private void doActionURL(String url) {
    while (!TextUtils.isEmpty(url) && url.startsWith("/")) {
      url = url.substring(1);
    }
    LauncherHelper.from(getContext())
        .startActivity(WebViewActivity.callIntent(getContext(), "", url));
  }
}
