package com.hzrcht.seaofflowers.module.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.home.HomePageActivity;

public class SelectGenderActivity extends BaseBarActivity {

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_select_gender_bar;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_gender);

    }

    @Override
    protected void loadData() {
        LinearLayout action_bar_center_actions = bindView(R.id.action_bar_center_actions);
        action_bar_center_actions.setOnClickListener(view -> getLaunchHelper().startActivity(HomePageActivity.class));

    }
}
