package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineHelpCenterPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineHelpCenterViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class MineHelpCenterActivity extends BaseBarActivity implements MineHelpCenterViewer {

    @PresenterLifeCycle
    private MineHelpCenterPresenter presenter = new MineHelpCenterPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_help_center_view);
    }

    @Override
    protected void loadData() {
        setTitle("用户帮助中心");
        bindView(R.id.ll_online_service, view -> {

        });

        bindView(R.id.ll_suggestion, view -> {
            getLaunchHelper().startActivity(MineSuggestionActivity.class);
        });
    }
}
