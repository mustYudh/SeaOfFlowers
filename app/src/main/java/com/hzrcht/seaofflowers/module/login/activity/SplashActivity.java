package com.hzrcht.seaofflowers.module.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseActivity;
import com.hzrcht.seaofflowers.module.login.activity.presenter.SplashPresenter;
import com.hzrcht.seaofflowers.module.login.activity.presenter.SplashViewer;
import com.hzrcht.seaofflowers.push.ThirdPushTokenMgr;
import com.tencent.imsdk.log.QLog;
import com.tencent.imsdk.utils.IMFunc;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

public class SplashActivity extends BaseActivity implements SplashViewer {

    @PresenterLifeCycle
    private SplashPresenter mPresenter = new SplashPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        mPresenter.handleCountDown();
    }

    @Override
    protected void loadData() {

        //推送
        if (IMFunc.isBrandVivo()) {
            // vivo 离线推送
            PushClient.getInstance(getActivity()).turnOnPush(new IPushActionListener() {
                @Override
                public void onStateChanged(int state) {
                    if (state == 0) {
                        String regId = PushClient.getInstance(getActivity()).getRegId();
                        QLog.i("推送", "vivopush open vivo push success regId = " + regId);
                        ThirdPushTokenMgr.getInstance().setThirdPushToken(regId);
                        ThirdPushTokenMgr.getInstance().setPushTokenToTIM();
                        ToastUtils.show("支持推送..." + state);
                    } else {
                        // 根据 vivo 推送文档说明，state = 101表示该 vivo 机型或者版本不支持 vivo 推送，详情请参考 vivo 推送常见问题汇总
                        QLog.i("推送", "vivopush open vivo push fail state = " + state);
                        ToastUtils.show("不支持推送..." + state);
                    }
                }
            });
        }

        if (IMFunc.isBrandHuawei()) {
            // 华为离线推送
            HMSAgent.connect(this, new ConnectHandler() {
                @Override
                public void onConnect(int rst) {
                    QLog.i("推送", "huawei push HMS connect end:" + rst);
                }
            });
            getHuaWeiPushToken();
        }
    }

    private void getHuaWeiPushToken() {
        HMSAgent.Push.getToken(new GetTokenHandler() {
            @Override
            public void onResult(int rtnCode) {
                Log.e("推送", "huawei push get token: end" + rtnCode);
            }
        });
    }
}
