package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineWithdrawPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineWithdrawViewer;
import com.hzrcht.seaofflowers.module.mine.adapter.MineWithdrawGvAdapter;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.ui.NoSlidingGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 提现
 */
public class MineMineWithdrawActivity extends BaseBarActivity implements MineWithdrawViewer {
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    private MineWithdrawPresenter presenter = new MineWithdrawPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_withdraw_view);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_withdraw_view_bar;
    }

    @Override
    protected void loadData() {
        for (int i = 0; i < 5; i++) {
            list.add("");
        }
        NoSlidingGridView gv_type = bindView(R.id.gv_type);
        MineWithdrawGvAdapter adapter = new MineWithdrawGvAdapter(list, getActivity());
        gv_type.setAdapter(adapter);
    }
}
