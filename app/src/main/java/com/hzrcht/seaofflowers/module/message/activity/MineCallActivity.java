package com.hzrcht.seaofflowers.module.message.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.message.activity.presenter.MineCallPresenter;
import com.hzrcht.seaofflowers.module.message.activity.presenter.MineCallViewer;
import com.hzrcht.seaofflowers.module.message.adapter.MineCallRvAdapter;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class MineCallActivity extends BaseBarActivity implements MineCallViewer {
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    private MineCallPresenter presenter = new MineCallPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_call_view);
    }

    @Override
    protected void loadData() {
        setTitle("我的通话");
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        RecyclerView rv_mine_call = bindView(R.id.rv_mine_call);
        rv_mine_call.setLayoutManager(new LinearLayoutManager(getActivity()));

        MineCallRvAdapter adapter = new MineCallRvAdapter(R.layout.item_mine_call, list, getActivity());
        rv_mine_call.setAdapter(adapter);
    }
}
