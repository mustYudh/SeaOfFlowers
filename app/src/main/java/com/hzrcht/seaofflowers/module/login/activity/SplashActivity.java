package com.hzrcht.seaofflowers.module.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseActivity;
import com.hzrcht.seaofflowers.module.login.activity.presenter.SplashPresenter;
import com.hzrcht.seaofflowers.module.login.activity.presenter.SplashViewer;
import com.yu.common.mvp.PresenterLifeCycle;

public class SplashActivity extends BaseActivity implements SplashViewer {
    private static final String TAG = "SplashActivity";
    @PresenterLifeCycle
    private SplashPresenter mPresenter = new SplashPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        mPresenter.handleCountDown();
    }

    @Override
    protected void loadData() {
//        if (IMFunc.isBrandVivo()) {
//            // vivo离线推送
//            PushClient.getInstance(getApplicationContext()).turnOnPush(new IPushActionListener() {
//                @Override
//                public void onStateChanged(int state) {
//                    if (state == 0) {
//                        String regId = PushClient.getInstance(getApplicationContext()).getRegId();
//
//                        ThirdPushTokenMgr.getInstance().setThirdPushToken(regId);
//                        ThirdPushTokenMgr.getInstance().setPushTokenToTIM();
//                    } else {
//
//                        Log.i(TAG, "vivopush open vivo push fail state = " + state);
//                    }
//                }
//            });
//        }
    }
}
