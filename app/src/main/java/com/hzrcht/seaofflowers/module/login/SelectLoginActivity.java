package com.hzrcht.seaofflowers.module.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseActivity;
import com.yu.common.ui.DelayClickTextView;

public class SelectLoginActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_login);
    }

    @Override
    protected void loadData() {
        DelayClickTextView tv_mobile = bindView(R.id.tv_mobile);
        DelayClickTextView tv_wechat = bindView(R.id.tv_wechat);
        DelayClickTextView tv_qq = bindView(R.id.tv_qq);

        tv_mobile.setOnClickListener(this);
        tv_wechat.setOnClickListener(this);
        tv_qq.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_mobile:
                getLaunchHelper().startActivity(SelectGenderActivity.class);
                break;
            case R.id.tv_wechat:
                break;
            case R.id.tv_qq:
                break;
        }
    }
}
