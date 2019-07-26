package com.hzrcht.seaofflowers.module.login.activity.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.hzrcht.seaofflowers.data.UserProfile;
import com.hzrcht.seaofflowers.http.ApiServices;
import com.hzrcht.seaofflowers.http.subscriber.TipRequestSubscriber;
import com.hzrcht.seaofflowers.module.home.HomePageActivity;
import com.hzrcht.seaofflowers.module.login.activity.SelectLoginActivity;
import com.hzrcht.seaofflowers.module.login.bean.UserSigBean;
import com.hzrcht.seaofflowers.module.login.bean.WxConfigBean;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.countdown.RxCountDown;
import com.yu.common.countdown.RxCountDownAdapter;
import com.yu.common.framework.BaseViewPresenter;

/**
 * @author yudneghao
 * @date 2019-06-03
 */
@SuppressLint("CheckResult")
public class SplashPresenter extends BaseViewPresenter<SplashViewer> {

    private RxCountDown rxCountDown = new RxCountDown();

    public SplashPresenter(SplashViewer viewer) {
        super(viewer);
    }

    public void getWxConfig() {
        XHttp.post(ApiServices.WXSYSCONFIG)
                .accessToken(false)
                .execute(WxConfigBean.class)
                .subscribeWith(new TipRequestSubscriber<WxConfigBean>() {
                    @Override
                    protected void onSuccess(WxConfigBean wxConfigBean) {
                        assert getViewer() != null;
                        getViewer().getWxConfigSuccess(wxConfigBean);
                    }

                    @Override
                    protected void onError(ApiException apiException) {
                        assert getViewer() != null;
                        getViewer().getWxConfigFail();
                    }
                });
    }


    public void handleCountDown() {
        rxCountDown.start(3);
        rxCountDown.setCountDownTimeListener(new RxCountDownAdapter() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                getHome();
            }

            @Override
            public void onNext(Integer time) {
                super.onNext(time);
            }

            @Override
            public void onComplete() {
                getHome();
                super.onComplete();
            }
        });
    }

    @SuppressLint("CheckResult")
    private void getHome() {
        if (UserProfile.getInstance().getApplogin()) {
            //登录了
            XHttp.post(ApiServices.GETUSERSIG)
                    .accessToken(true)
                    .execute(UserSigBean.class)
                    .subscribeWith(new TipRequestSubscriber<UserSigBean>() {
                        @Override
                        protected void onSuccess(UserSigBean userSigBean) {
                            loginIm(userSigBean.UserSig);
                        }

                        @Override
                        protected void onError(ApiException apiException) {
                            UserProfile.getInstance().setApplogin(false);
                            getLauncherHelper().startActivity(SelectLoginActivity.class);
                            getActivity().finish();
                        }
                    });


        } else {
            //未登录
            getLauncherHelper().startActivity(SelectLoginActivity.class);
            getActivity().finish();
        }

    }

    private void loginIm(String userSig) {
        // identifier为用户名，userSig 为用户登录凭证
        TIMManager.getInstance().login(UserProfile.getInstance().getUserId() + "", userSig, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.e("im", "登录失败了....." + code + "..." + desc);

                UserProfile.getInstance().setApplogin(false);
                getLauncherHelper().startActivity(SelectLoginActivity.class);
                getActivity().finish();
            }

            @Override
            public void onSuccess() {
                getLauncherHelper().startActivity(HomePageActivity.class);
                getActivity().finish();
            }
        });
    }

    @Override
    public void willDestroy() {
        super.willDestroy();
        if (rxCountDown != null) {
            rxCountDown.stop();
        }
    }
}
