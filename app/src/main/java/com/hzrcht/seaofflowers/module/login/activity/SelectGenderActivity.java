package com.hzrcht.seaofflowers.module.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.home.HomePageActivity;
import com.yu.common.ui.DelayClickTextView;

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
        DelayClickTextView tv_sure = bindView(R.id.tv_sure);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLaunchHelper().startActivity(HomePageActivity.class);
            }
        });

    }
}
