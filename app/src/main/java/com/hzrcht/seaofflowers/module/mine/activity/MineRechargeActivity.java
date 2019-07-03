package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineRechargePresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineRechargeViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class MineRechargeActivity extends BaseBarActivity implements MineRechargeViewer {

    @PresenterLifeCycle
    private MineRechargePresenter presenter = new MineRechargePresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_recharge_view);
    }

    @Override
    protected void loadData() {

    }
}
