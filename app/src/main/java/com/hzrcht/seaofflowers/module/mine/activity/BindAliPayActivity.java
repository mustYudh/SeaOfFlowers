package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.BindAliPayPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.BindAliPayViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class BindAliPayActivity extends BaseBarActivity implements BindAliPayViewer {

    @PresenterLifeCycle
    private BindAliPayPresenter presenter = new BindAliPayPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_bind_ali_pay_view);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_bind_ali_pay_view_bar;
    }

    @Override
    protected void loadData() {
        setTitle("绑定支付宝");
    }
}
