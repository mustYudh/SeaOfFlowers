package com.hzrcht.seaofflowers.keep.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * @author chenwei
 * @date 2018/5/22
 */
public class KeepLiveManager {

  /**
   * 单例模式
   */
  private static KeepLiveManager instance;

  public static KeepLiveManager getInstance() {
    if (instance == null) {
      instance = new KeepLiveManager();
    }
    return instance;
  }

  private LockReceiver lockReceiver;

  /**
   * 注册锁屏/解锁广播
   */
  public void registerReceiver(Context context) {
    if (context != null && lockReceiver == null) {
      lockReceiver = new LockReceiver();
      context.registerReceiver(lockReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }
  }

  /**
   * 注销锁屏/解锁广播
   */
  public void unRegisterReceiver(Context context) {
    if (lockReceiver != null && context != null) {
      try {
        context.unregisterReceiver(lockReceiver);
        lockReceiver = null;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  class LockReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
      Log.i("KeepService", "LockReceiver onReceive " + intent.getAction());
      switch (String.valueOf(intent.getAction())) {
        case Intent.ACTION_SCREEN_OFF:
          startLiveActivity(context);
          break;
        default:
          break;
      }
    }
  }

  private void startLiveActivity(Context context) {
    try {
      Intent intent = new Intent(context, PixelActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
