package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineRedactDataPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineRedactDataViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class MineRedactDataActivity extends BaseBarActivity implements MineRedactDataViewer {

    @PresenterLifeCycle
    private MineRedactDataPresenter presenter = new MineRedactDataPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_redact_data_view);
    }

    @Override
    protected void loadData() {

    }
}
