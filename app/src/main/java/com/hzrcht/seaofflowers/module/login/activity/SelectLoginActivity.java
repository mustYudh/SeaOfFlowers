package com.hzrcht.seaofflowers.module.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseActivity;
import com.hzrcht.seaofflowers.module.home.HomePageActivity;
import com.umeng.socialize.UMShareAPI;
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
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), 1);
                break;
            case R.id.tv_wechat:
                break;
            case R.id.tv_qq:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                int type = data.getIntExtra("type", 0);
                if (type == 0) {
                    startActivityForResult(new Intent(getActivity(), SelectGenderActivity.class), 1);
                } else {
                    getLaunchHelper().startActivity(HomePageActivity.class);
                    finish();
                }
                break;
            case 2:
                getLaunchHelper().startActivity(HomePageActivity.class);
                finish();
                break;
        }
    }
}
