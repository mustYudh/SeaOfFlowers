package com.hzrcht.seaofflowers.http.interceptor;

import android.text.TextUtils;
import com.hzrcht.seaofflowers.data.UserProfile;
import com.xuexiang.xhttp2.interceptor.BaseDynamicInterceptor;
import java.io.IOException;
import java.util.TreeMap;
import okhttp3.Response;

public class CustomDynamicInterceptor extends BaseDynamicInterceptor<CustomDynamicInterceptor> {

  @Override
  protected TreeMap<String, Object> updateDynamicParams(TreeMap<String, Object> dynamicMap) {
    String token = UserProfile.getInstance().getAppToken();
    if (isAccessToken() && !TextUtils.isEmpty(token)) {
      dynamicMap.put("token", token);
    }
    return dynamicMap;
  }

  @Override public Response intercept(Chain chain) throws IOException {
    return super.intercept(chain);
  }
}
