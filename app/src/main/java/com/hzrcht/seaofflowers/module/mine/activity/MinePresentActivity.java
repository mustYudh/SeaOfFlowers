package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MinePresentRvAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MinePresentPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MinePresentViewer;
import com.hzrcht.seaofflowers.module.view.ScreenSpaceItemDecoration;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class MinePresentActivity extends BaseBarActivity implements MinePresentViewer {
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    private MinePresentPresenter presenter = new MinePresentPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_present_view);
    }

    @Override
    protected void loadData() {
        setTitle("礼物柜");
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        RecyclerView rv_present = bindView(R.id.rv_present);
        rv_present.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rv_present.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 10, 4));
        MinePresentRvAdapter adapter = new MinePresentRvAdapter(R.layout.item_mine_present, list, getActivity());
        rv_present.setAdapter(adapter);
    }
}
