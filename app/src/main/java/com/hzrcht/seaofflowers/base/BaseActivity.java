package com.hzrcht.seaofflowers.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.utils.ActivityManager;
import com.hzrcht.seaofflowers.utils.DialogUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;
import com.yu.common.framework.BasicActivity;

public abstract class BaseActivity extends BasicActivity {
    protected DialogUtils loadDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
        DialogUtils.Builder builder = new DialogUtils.Builder(this);
        loadDialog = builder.view(R.layout.dialog_wait)
                .gravity(Gravity.CENTER)
                .cancelTouchout(false)
                .style(R.style.Dialog_NoAnimation)
                .build();
    }

    @Override
    protected void handleNetWorkError(View view) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        super.onDestroy();
    }
}
