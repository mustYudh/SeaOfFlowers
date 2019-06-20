package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineBalancePresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineBalanceViewer;
import com.hzrcht.seaofflowers.module.mine.adapter.MineBalanceRvAdapter;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class MineBalanceActivity extends BaseBarActivity implements MineBalanceViewer {
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    private MineBalancePresenter presenter = new MineBalancePresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_balance_view);
    }

    @Override
    protected void loadData() {
        setTitle("账号余额");
        for (int i = 0; i < 20; i++) {
            list.add("");
        }
        RecyclerView rv_balance = bindView(R.id.rv_balance);
        rv_balance.setLayoutManager(new LinearLayoutManager(getActivity()));
        MineBalanceRvAdapter adapter = new MineBalanceRvAdapter(R.layout.item_mine_balance, list, getActivity());
        rv_balance.setAdapter(adapter);
    }
}
