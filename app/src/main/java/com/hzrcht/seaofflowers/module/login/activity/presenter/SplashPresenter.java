package com.hzrcht.seaofflowers.module.login.activity.presenter;

import com.hzrcht.seaofflowers.data.UserProfile;
import com.hzrcht.seaofflowers.module.home.HomePageActivity;
import com.hzrcht.seaofflowers.module.login.activity.SelectLoginActivity;
import com.yu.common.countdown.RxCountDown;
import com.yu.common.countdown.RxCountDownAdapter;
import com.yu.common.framework.BaseViewPresenter;

/**
 * @author yudneghao
 * @date 2019-06-03
 */
public class SplashPresenter extends BaseViewPresenter<SplashViewer> {

    private RxCountDown rxCountDown = new RxCountDown();

    public SplashPresenter(SplashViewer viewer) {
        super(viewer);
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

    private void getHome() {
        if (UserProfile.getInstance().isAppLogin()) {
            //登录了
            getLauncherHelper().startActivity(HomePageActivity.class);
        } else {
            //未登录
            getLauncherHelper().startActivity(SelectLoginActivity.class);
        }
        getActivity().finish();
    }

    @Override
    public void willDestroy() {
        super.willDestroy();
        if (rxCountDown != null) {
            rxCountDown.stop();
        }
    }
}
