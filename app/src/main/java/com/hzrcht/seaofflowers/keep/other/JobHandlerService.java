package com.hzrcht.seaofflowers.keep.other;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.LOLLIPOP) public class JobHandlerService extends JobService {

  private JobScheduler mJobScheduler;

  @Override public void onCreate() {
    super.onCreate();
    Log.i("KeepService", getClass().getSimpleName() + " onCreate");
    KeepLiveManager.getInstance().registerReceiver(getApplicationContext());
  }

  @Override public void onDestroy() {
    Log.i("KeepService", getClass().getSimpleName() + " onDestroy");
    KeepLiveManager.getInstance().unRegisterReceiver(getApplicationContext());
    super.onDestroy();
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    startService(new Intent(this, LocalService.class));
    startService(new Intent(this, RemoteService.class));

    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

        if (mJobScheduler != null) {
          mJobScheduler.cancel(5);
        }
        mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(5,
            new ComponentName(getPackageName(), JobHandlerService.class.getName()));
        //每隔15秒运行一次
        builder.setPeriodic(15000);
        builder.setRequiresCharging(true);
        //设置设备重启后，是否重新执行任务
        builder.setPersisted(true);
        builder.setRequiresDeviceIdle(true);

        if (mJobScheduler.schedule(builder.build()) == JobScheduler.RESULT_SUCCESS) {
          Log.i("KeepService", getClass().getSimpleName() + " 工作成功");
        } else {
          stopSelf();
          Log.i("KeepService", getClass().getSimpleName() + " 工作失败");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return START_STICKY;
  }

  @Override public boolean onStartJob(JobParameters params) {
    startService(new Intent(this, LocalService.class));
    startService(new Intent(this, RemoteService.class));
    return false;
  }

  @Override public boolean onStopJob(JobParameters params) {
    startService(new Intent(this, LocalService.class));
    startService(new Intent(this, RemoteService.class));
    return false;
  }
}