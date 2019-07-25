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
public class LocalService extends Service {
  private MyBinder myBinder;
  private MyServiceConnection conn;

  @Override public IBinder onBind(Intent intent) {
    return (IBinder) myBinder;
  }

  @Override public void onCreate() {
    super.onCreate();
    init();
  }

  private void init() {
    myBinder = new MyBinder();
    conn = new MyServiceConnection();
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    Intent intents = new Intent(this, RemoteService.class);
    bindService(intents, conn, Context.BIND_IMPORTANT);
    return START_STICKY;
  }

  class MyBinder extends KeepService_1.Stub {

    @Override public String getName() throws RemoteException {
      return "local_binder";
    }
  }

  class MyServiceConnection implements ServiceConnection {

    @Override public void onServiceConnected(ComponentName name, IBinder service) {
      Log.i("KeepService", "LocalService  onServiceConnected");
    }

    @Override public void onServiceDisconnected(ComponentName name) {
      Log.i("KeepService", "LocalService  onServiceDisconnected");
      LocalService.this.startService(new Intent(LocalService.this, RemoteService.class));
    }
  }
}