package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.TRTCMainActivityPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.TRTCMainActivityViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class TRTCMainActivity extends BaseBarActivity implements TRTCMainActivityViewer {

    @PresenterLifeCycle
    private TRTCMainActivityPresenter presenter = new TRTCMainActivityPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_trtcmain_view);
    }

    @Override
    protected void loadData() {

    }


}
