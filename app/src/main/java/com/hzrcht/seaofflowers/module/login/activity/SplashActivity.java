package com.hzrcht.seaofflowers.module.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hzrcht.seaofflowers.APP;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseActivity;
import com.hzrcht.seaofflowers.module.login.activity.presenter.SplashPresenter;
import com.hzrcht.seaofflowers.module.login.activity.presenter.SplashViewer;
import com.hzrcht.seaofflowers.module.login.bean.WxConfigBean;
import com.hzrcht.seaofflowers.offline.ThirdPushTokenMgr;
import com.tencent.imsdk.utils.IMFunc;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.share.Key;
import com.yu.share.ShareAuthSDK;

import static com.hzrcht.seaofflowers.APP.DEBUG;

public class SplashActivity extends BaseActivity implements SplashViewer {
    private static final String TAG = "SplashActivity";
    @PresenterLifeCycle
    private SplashPresenter mPresenter = new SplashPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void loadData() {
        //获取微信配置
        mPresenter.getWxConfig();
        if (IMFunc.isBrandVivo()) {
            // vivo离线推送
            PushClient.getInstance(getApplicationContext()).turnOnPush(new IPushActionListener() {
                @Override
                public void onStateChanged(int state) {
                    if (state == 0) {
                        String regId = PushClient.getInstance(getApplicationContext()).getRegId();

                        ThirdPushTokenMgr.getInstance().setThirdPushToken(regId);
                        ThirdPushTokenMgr.getInstance().setPushTokenToTIM();
                    } else {

                        Log.i(TAG, "vivopush open vivo push fail state = " + state);
                    }
                }
            });
        }
    }

    @Override
    public void getWxConfigSuccess(WxConfigBean wxConfigBean) {
        if (wxConfigBean != null) {
            Key.WX_APP_PAY_ID = wxConfigBean.appid;
            Key.WX_APP_PAY_KEY = wxConfigBean.app_secret;

            ShareAuthSDK.init(APP.getInstance(), DEBUG);

            mPresenter.handleCountDown();
        } else {
            ToastUtils.show("初始化失败,请重试");
            finish();
        }

    }

    @Override
    public void getWxConfigFail() {
        ToastUtils.show("初始化失败,请重试");
        finish();
    }
}
