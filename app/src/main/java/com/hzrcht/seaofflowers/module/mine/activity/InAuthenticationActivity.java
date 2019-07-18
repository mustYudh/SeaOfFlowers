package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.InAuthenticationPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.InAuthenticationViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class InAuthenticationActivity extends BaseBarActivity implements InAuthenticationViewer {

    @PresenterLifeCycle
    private InAuthenticationPresenter presenter = new InAuthenticationPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_in_authentication_view);
    }

    @Override
    protected void loadData() {
        setTitle("主播资料审核中");

        bindView(R.id.tv_commit, view -> {
            getLaunchHelper().startActivity(MineRedactDataActivity.class);
            finish();
        });
    }
}
