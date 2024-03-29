package com.yu.common.utils;

import android.content.Context;
import android.content.res.Resources;

public class DensityUtil {


  public static int px2dip(float pxValue) {
    final float scale = Resources.getSystem().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }


  @Deprecated public static int dip2px(Context context, float dipValue) {
    return dip2px(dipValue);
  }

  public static int dip2px(float dipValue) {
    final float scale = Resources.getSystem().getDisplayMetrics().density;
    return (int) (dipValue * scale + 0.5f);
  }


  @Deprecated public static int px2sp(Context context, float pxValue) {
    return px2sp(pxValue);
  }

  public static int px2sp(float pxValue) {
    final float scale = Resources.getSystem().getDisplayMetrics().scaledDensity;
    return (int) (pxValue / scale + 0.5f);
  }


  @Deprecated public static int sp2px(Context context, float spValue) {
    return sp2px(spValue);
  }

  public static int sp2px(float spValue) {
    final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
    return (int) (spValue * fontScale + 0.5f);
  }


  public static int getScreenWidth() {
    return Resources.getSystem().getDisplayMetrics().widthPixels;
  }


  public static int getScreenHeight() {
    return Resources.getSystem().getDisplayMetrics().heightPixels;
  }
}