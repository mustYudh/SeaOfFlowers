package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.WithdrawPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.WithdrawViewer;
import com.yu.common.mvp.PresenterLifeCycle;

/**
 * 提现
 */
public class WithdrawActivity extends BaseBarActivity implements WithdrawViewer {

    @PresenterLifeCycle
    private WithdrawPresenter presenter = new WithdrawPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_withdraw_view);
    }

    @Override
    protected void loadData() {

    }
}
