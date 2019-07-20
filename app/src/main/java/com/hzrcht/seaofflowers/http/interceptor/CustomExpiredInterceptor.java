package com.hzrcht.seaofflowers.http.interceptor;

import com.hzrcht.seaofflowers.utils.ActivityManager;
import com.xuexiang.xhttp2.interceptor.BaseExpiredInterceptor;
import com.xuexiang.xhttp2.model.ApiResult;
import com.xuexiang.xhttp2.model.ExpiredInfo;
import com.xuexiang.xhttp2.utils.HttpUtils;
import com.xuexiang.xhttp2.utils.JSONUtils;

import okhttp3.Response;

public class CustomExpiredInterceptor extends BaseExpiredInterceptor {

    private final static int TOKEN_FAILURE_CODE_1_FOR_SERVICES = 400;
    private final static int TOKEN_FAILURE_CODE_2_FOR_SERVICES = 401;
    private final static int TOKEN_FAILURE_CODE_FOR_APP = -1166;

    @Override
    protected ExpiredInfo isResponseExpired(Response oldResponse, String bodyString) {
        int code = JSONUtils.getInt(bodyString, ApiResult.CODE, 0);
        ExpiredInfo expiredInfo = new ExpiredInfo(code);
        switch (code) {
            case TOKEN_FAILURE_CODE_1_FOR_SERVICES:
                expiredInfo.setExpiredType(TOKEN_FAILURE_CODE_FOR_APP);
                expiredInfo.setBodyString(bodyString);
                break;
            case TOKEN_FAILURE_CODE_2_FOR_SERVICES:
                expiredInfo.setExpiredType(TOKEN_FAILURE_CODE_FOR_APP);
                expiredInfo.setBodyString(bodyString);
                break;
            default:
        }
        return expiredInfo;
    }

    @Override
    protected Response responseExpired(Response oldResponse, Chain chain, ExpiredInfo expiredInfo) {
        Response response = null;
        switch (expiredInfo.getExpiredType()) {
            case TOKEN_FAILURE_CODE_FOR_APP:
                ActivityManager.getInstance().reLogin();
                response = HttpUtils.getErrorResponse(oldResponse, expiredInfo.getCode(), "登录失效,需要重新登录!");
                break;
            default:
        }
        return response;
    }
}
