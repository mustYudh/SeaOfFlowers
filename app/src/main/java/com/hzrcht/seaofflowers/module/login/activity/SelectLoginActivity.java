package com.hzrcht.seaofflowers.module.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseActivity;
import com.hzrcht.seaofflowers.data.UserProfile;
import com.hzrcht.seaofflowers.module.home.HomePageActivity;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
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
                    loginIm();
                }
                break;
            case 2:
                loginIm();
                break;
        }
    }


    private void loginIm() {
        // identifier为用户名，userSig 为用户登录凭证
        TIMManager.getInstance().login(UserProfile.getInstance().getUserId() + "", UserProfile.getInstance().getUserSig(), new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.e("im", "登录失败了....." + code + "..." + desc);
            }

            @Override
            public void onSuccess() {
                Log.e("im", "登录成功了");
                getLaunchHelper().startActivity(HomePageActivity.class);
                finish();
            }
        });
    }
}
