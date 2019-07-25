package com.hzrcht.seaofflowers.keep.other;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * @author chenwei
 * @date 2018/5/22
 */
public class KeepService {

  public static void startKeepService(Context context) {
    /**
     * 开启JobService保活
     */
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Intent intent = new Intent(context, JobHandlerService.class);
      context.startService(intent);
    } else {
      Intent intent = new Intent(context, GrayService.class);
      context.startService(intent);
    }
  }

}
