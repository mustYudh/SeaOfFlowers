package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.ChargeSettingPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.ChargeSettingViewer;
import com.yu.common.mvp.PresenterLifeCycle;

/**
 * 收费设置
 */
public class ChargeSettingActivity extends BaseBarActivity implements ChargeSettingViewer {

    @PresenterLifeCycle
    private ChargeSettingPresenter presenter = new ChargeSettingPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_charge_setting_view);
    }

    @Override
    protected void loadData() {
        setTitle("收费设置");

    }
}
