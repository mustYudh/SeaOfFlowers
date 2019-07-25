package com.hzrcht.seaofflowers.keep.other;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author chenwei
 */
public class PixelActivity extends Activity {

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Window window = getWindow();
    window.setGravity(Gravity.LEFT | Gravity.TOP);
    WindowManager.LayoutParams params = window.getAttributes();
    params.x = 0;
    params.y = 0;
    params.height = 1;
    params.width = 1;
    window.setAttributes(params);

    registerReceiver();
    Log.i("KeepService", getClass().getSimpleName() + " onCreate");
  }

  @Override protected void onResume() {
    super.onResume();
    if (isScreenOn()) {
      stopSelf();
    }
  }

  private boolean isScreenOn() {
    PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
    //true为打开，false为关闭
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
      return powerManager.isInteractive();
    } else {
      return powerManager.isScreenOn();
    }
  }

  private InnerReceiver innerReceiver;

  /**
   * 注销锁屏/解锁广播
   */
  public void registerReceiver() {
    innerReceiver = new InnerReceiver();
    IntentFilter filter = new IntentFilter();
    filter.addAction(Intent.ACTION_USER_PRESENT);
    registerReceiver(innerReceiver, filter);
  }

  public void unRegisterReceiver() {
    if (innerReceiver != null) {
      unregisterReceiver(innerReceiver);
      innerReceiver = null;
    }
  }

  class InnerReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
      Log.i("KeepService", "LockReceiver onReceive " + intent.getAction());
      switch (String.valueOf(intent.getAction())) {
        case Intent.ACTION_USER_PRESENT:
          stopSelf();
          break;
        default:
          break;
      }
    }
  }

  private void stopSelf() {
    finish();
    unRegisterReceiver();
  }

  @Override protected void onPause() {
    super.onPause();
    if (isFinishing()) {
      unRegisterReceiver();
    }
  }

  @Override protected void onDestroy() {
    Log.i("KeepService", getClass().getSimpleName() + " onDestroy");
    KeepService.startKeepService(getApplicationContext());
    unRegisterReceiver();
    super.onDestroy();
  }
}