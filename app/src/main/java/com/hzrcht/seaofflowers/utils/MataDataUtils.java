package com.hzrcht.seaofflowers.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import java.util.HashMap;


public class MataDataUtils {
  private static HashMap<String, String> metaDataMap = new HashMap<>();

  /**
   * 获取application中指定的meta-data
   *
   * @return 如果没有获取成功(没有对应值, 或者异常)，则返回值为空
   */
  public static String getAppMetaData(Context ctx, String key) {
    if (ctx == null || TextUtils.isEmpty(key)) {
      return null;
    }

    if (!TextUtils.isEmpty(metaDataMap.get(key))) {
      return metaDataMap.get(key);
    }

    String resultData = null;
    try {
      PackageManager packageManager = ctx.getPackageManager();
      if (packageManager != null) {
        ApplicationInfo applicationInfo =
            packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
        if (applicationInfo != null) {
          if (applicationInfo.metaData != null) {
            resultData = String.valueOf(applicationInfo.metaData.get(key));
          }
        }
      }
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }

    if ("UMENG_CHANNEL".equals(key) && TextUtils.isEmpty(resultData)) {
      resultData = getDefaultUmengChannel();
    }

    metaDataMap.put(key, resultData);
    return resultData;
  }

  /**
   * 默认友盟渠道号
   */
  private static String getDefaultUmengChannel() {
    return "x0";
  }
}
