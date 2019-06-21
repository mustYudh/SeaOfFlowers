package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.ApplyAuthenticationPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.ApplyAuthenticationViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class ApplyAuthenticationActivity extends BaseBarActivity implements ApplyAuthenticationViewer {

    @PresenterLifeCycle
    private ApplyAuthenticationPresenter presenter = new ApplyAuthenticationPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_apply_authentication_view);
    }

    @Override
    protected void loadData() {
        setTitle("申请认证");

    }
}
