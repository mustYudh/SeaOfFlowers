package com.hzrcht.seaofflowers;

import android.os.Build;

import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.interceptor.CustomDynamicInterceptor;
import com.hzrcht.seaofflowers.http.interceptor.CustomExpiredInterceptor;
import com.hzrcht.seaofflowers.http.interceptor.CustomLoggingInterceptor;
import com.hzrcht.seaofflowers.keep.other.KeepService;
import com.hzrcht.seaofflowers.utils.CheckVersionCodeUtils;
import com.hzrcht.seaofflowers.utils.MataDataUtils;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.session.SessionWrapper;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.config.CustomFaceConfig;
import com.tencent.qcloud.tim.uikit.config.GeneralConfig;
import com.tencent.qcloud.tim.uikit.config.TUIKitConfigs;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.XHttpSDK;
import com.xuexiang.xhttp2.model.HttpHeaders;
import com.yu.common.CommonInit;
import com.yu.common.base.BaseApp;
import com.yu.share.ShareAuthSDK;

/**
 * @author yudneghao
 * @date 2019-06-03
 */
public class APP extends BaseApp {
    public static final int NET_TYPE = BuildConfig.API_MODE;
    public static final boolean DEBUG = APP.NET_TYPE == 0;
    private static APP instance;

    @Override
    public void onCreate() {
        APP.instance = this;
        super.onCreate();
        CommonInit.init(this);
        ShareAuthSDK.init(this, DEBUG);
        initHttp();
        try {
            KeepService.startKeepService(this);
            //if (LeakCanary.isInAnalyzerProcess(this)) {
            //  return;
            //}
            //LeakCanary.install(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        //初始化 SDK 基本配置
//        TIMSdkConfig config = new TIMSdkConfig(ApiServices.SDKAPPID)
//                // .setAccoutType(accountType)     // 该接口已废弃
//                .enableLogPrint(true)              // 是否在控制台打印Log?
//                .setLogLevel(TIMLogLevel.DEBUG)    // Log输出级别（debug级别会很多）
//                .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/");
//        // Log文件存放在哪里？
//
//        //初始化 SDK
//        TIMManager.getInstance().init(this, config);

        // 配置一些Config，按需配置
        TUIKitConfigs configs = TUIKit.getConfigs();
        configs.setSdkConfig(new TIMSdkConfig(ApiServices.SDKAPPID));
        configs.setCustomFaceConfig(new CustomFaceConfig());
        configs.setGeneralConfig(new GeneralConfig());

        //判断是否是在主线程
        if (SessionWrapper.isMainProcess(this)) {
            /**
             * TUIKit的初始化函数
             *
             * @param context  应用的上下文，一般为对应应用的ApplicationContext
             * @param sdkAppID 您在腾讯云注册应用时分配的sdkAppID
             * @param configs  TUIKit的相关配置项，一般使用默认即可，需特殊配置参考API文档
             */
            TUIKit.init(this, ApiServices.SDKAPPID, configs);
//            if (IMFunc.isBrandXiaoMi()) {
//                // 小米离线推送
//                MiPushClient.registerPush(this, Constants.XM_PUSH_APPID, Constants.XM_PUSH_APPKEY);
//            }
//            if (IMFunc.isBrandHuawei()) {
//                // 华为离线推送
//                HMSAgent.init(this);
//            }
//            if (MzSystemUtils.isBrandMeizu(this)) {
//                // 魅族离线推送
//                PushManager.register(this, ApiServices.MZ_PUSH_APPID, ApiServices.MZ_PUSH_APPKEY);
//            }
//            if (IMFunc.isBrandVivo()) {
//                // vivo离线推送
//                PushClient.getInstance(this).initialize();
//            }

//            registerActivityLifecycleCallbacks(new StatisticActivityLifecycleCallback());
        }

    }

    private void initHttp() {
        XHttpSDK.init(this);
        if (DEBUG) {
            XHttpSDK.debug();
            XHttpSDK.debug(new CustomLoggingInterceptor());
        }
        XHttpSDK.setBaseUrl(getBaseUrl());
        XHttpSDK.setSubUrl(getSubUrl());
        XHttpSDK.addInterceptor(new CustomDynamicInterceptor().accessToken(true));
        XHttpSDK.addInterceptor(new CustomExpiredInterceptor());
        XHttp.getInstance().setTimeout(60000);
        XHttp.getInstance().setRetryCount(3);
        XHttp.getInstance().addCommonHeaders(getHttpHeaders());


    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("ua", CheckVersionCodeUtils.getVersionCode(this) + ";API1;android;" + Build.VERSION.RELEASE);
        headers.put("channel", MataDataUtils.getAppMetaData(APP.getInstance(), "UMENG_CHANNEL"));
        return headers;
    }

    private String getBaseUrl() {
        if (APP.NET_TYPE == 1) {
            return "http://huahai.hzrcht.com";
        } else if (APP.NET_TYPE == 2) {
            return "http://huahai.hzrcht.com";
        } else {
            return "http://huahai.hzrcht.com";
        }
    }

    public String getSubUrl() {
        return "";
    }

    public synchronized static APP getInstance() {
        return instance;
    }
}
