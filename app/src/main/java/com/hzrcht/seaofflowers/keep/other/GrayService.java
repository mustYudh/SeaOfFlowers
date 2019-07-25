package com.hzrcht.seaofflowers.keep.other;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author chenwei
 */
public class GrayService extends Service {
  private final static int GRAY_SERVICE_ID = 1223;

  @Override public int onStartCommand(Intent intent, int flags, int startId) {

    startService(new Intent(this, LocalService.class));
    startService(new Intent(this, RemoteService.class));

    Log.i("KeepService", "GrayService onStartCommand");

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
      //API < 18 ，此方法能有效隐藏Notification上的图标
      startForeground(GRAY_SERVICE_ID, new Notification());
    } else {
      Intent innerIntent = new Intent(this, GrayInnerService.class);
      startService(innerIntent);
      startForeground(GRAY_SERVICE_ID, new Notification());
    }
    return Service.START_STICKY;
  }

  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }

  /**
   * 给 API >= 18 的平台上用的灰色保活手段
   */
  public static class GrayInnerService extends Service {
    @Override public int onStartCommand(Intent intent, int flags, int startId) {

      Log.i("KeepService", "GrayInnerService onStartCommand");

      startForeground(GRAY_SERVICE_ID, new Notification());
      stopForeground(true);
      stopSelf();
      return super.onStartCommand(intent, flags, startId);
    }

    @Nullable @Override public IBinder onBind(Intent intent) {
      return null;
    }
  }
}
