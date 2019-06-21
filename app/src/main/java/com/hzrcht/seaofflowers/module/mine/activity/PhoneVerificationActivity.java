package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.PhoneVerificationPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.PhoneVerificationViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class PhoneVerificationActivity extends BaseBarActivity implements PhoneVerificationViewer {

    @PresenterLifeCycle
    private PhoneVerificationPresenter presenter = new PhoneVerificationPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_phone_verification_view);
    }

    @Override
    protected void loadData() {
        setTitle("手机号验证");
    }
}
