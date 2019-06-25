package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineIntimacyPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineIntimacyViewer;
import com.hzrcht.seaofflowers.module.mine.adapter.MineIntimacyRvAdapter;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class MineIntimacyActivity extends BaseBarActivity implements MineIntimacyViewer {
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    private MineIntimacyPresenter presenter = new MineIntimacyPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_intimacy_view);
    }

    @Override
    protected void loadData() {
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        RecyclerView rv_intimacy = bindView(R.id.rv_intimacy);
        rv_intimacy.setLayoutManager(new LinearLayoutManager(getActivity()));
        MineIntimacyRvAdapter adapter = new MineIntimacyRvAdapter(R.layout.item_mine_intimacy, list, getActivity());
        rv_intimacy.setAdapter(adapter);
    }
}
