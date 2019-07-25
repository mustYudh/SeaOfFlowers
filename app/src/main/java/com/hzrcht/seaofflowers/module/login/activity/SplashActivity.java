package com.hzrcht.seaofflowers.module.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseActivity;
import com.hzrcht.seaofflowers.module.login.activity.presenter.SplashPresenter;
import com.hzrcht.seaofflowers.module.login.activity.presenter.SplashViewer;
import com.yu.common.mvp.PresenterLifeCycle;

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
    }
}
