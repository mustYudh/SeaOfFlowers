package com.hzrcht.seaofflowers.keep.other;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.hzrcht.seaofflowers.KeepService_1;

/**
 * @author chenwei
 */
public class RemoteService extends Service {
  private MyBinder binder;
  private MyConnection conn;

  @Override public void onCreate() {
    super.onCreate();
    init();
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {

    Log.i("KeepService", getClass().getSimpleName() + " onStartCommand");

    Intent intents = new Intent(this, LocalService.class);
    bindService(intents, conn, Context.BIND_IMPORTANT);
    return START_STICKY;
  }

  private void init() {
    conn = new MyConnection();
    binder = new MyBinder();
  }

  @Override public IBinder onBind(Intent intent) {
    return binder;
  }

  static class MyBinder extends KeepService_1.Stub {

    @Override public String getName() throws RemoteException {
      return "remote_binder";
    }
  }

  class MyConnection implements ServiceConnection {

    @Override public void onServiceConnected(ComponentName name, IBinder service) {
      Log.i("KeepService", "RemoteService onStartCommand");
    }

    @Override public void onServiceDisconnected(ComponentName nme) {
      Log.i("KeepService", "RemoteService onServiceDisconnected");
      RemoteService.this.startService(new Intent(RemoteService.this, LocalService.class));
    }
  }
}