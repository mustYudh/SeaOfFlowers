package com.hzrcht.seaofflowers.module.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.mine.activity.ApplyAuthenticationActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MineAttentionActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MineBalanceActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MineRedactDataActivity;
import com.hzrcht.seaofflowers.module.mine.activity.SystemSettingsActivity;
import com.hzrcht.seaofflowers.module.view.MyOneLineView;

public class MineFragment extends BaseFragment implements View.OnClickListener {


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        ImageView iv_redact = bindView(R.id.iv_redact);
        LinearLayout ll_attention = bindView(R.id.ll_attention);
        LinearLayout ll_balance = bindView(R.id.ll_balance);
        LinearLayout ll_authentication = bindView(R.id.ll_authentication);
        MyOneLineView view_invitation_code = bindView(R.id.view_invitation_code);
        MyOneLineView view_accept_apprentice = bindView(R.id.view_accept_apprentice);
        MyOneLineView view_open_msg = bindView(R.id.view_open_msg);
        MyOneLineView view_system_settings = bindView(R.id.view_system_settings);


        view_invitation_code.initMine(R.drawable.ic_invitation_code, "填写好友邀请码", true, true);
        view_accept_apprentice.initMine(R.drawable.ic_accept_apprentice, "收徒赚钱", true, true);
        view_open_msg.initMine(R.drawable.ic_open_msg, "开启勿扰", false, true);
        view_open_msg.showSwitchView(true);
        view_open_msg.setSwichButton(true);
        view_system_settings.initMine(R.drawable.ic_system_settings, "系统设置", true, true);

        //点击事件
        iv_redact.setOnClickListener(this);
        ll_attention.setOnClickListener(this);
        ll_balance.setOnClickListener(this);
        ll_authentication.setOnClickListener(this);
        view_system_settings.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_redact:
                getLaunchHelper().startActivity(MineRedactDataActivity.class);
                break;
            case R.id.ll_attention:
                getLaunchHelper().startActivity(MineAttentionActivity.class);
                break;
            case R.id.ll_balance:
                getLaunchHelper().startActivity(MineBalanceActivity.class);
                break;
            case R.id.ll_authentication:
                getLaunchHelper().startActivity(ApplyAuthenticationActivity.class);
//                getLaunchHelper().startActivity(InAuthenticationActivity.class);
                break;
            case R.id.view_system_settings:
                getLaunchHelper().startActivity(SystemSettingsActivity.class);
                break;
        }
    }
}
