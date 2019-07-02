package com.hzrcht.seaofflowers.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.home.adapter.HomeNearbyDistanceRvAdapter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeNearbyDistancePresenter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeNearbyDistanceViewer;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class HomeNearbyDistanceFragment extends BaseFragment implements HomeNearbyDistanceViewer {
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    private HomeNearbyDistancePresenter mPresenter = new HomeNearbyDistancePresenter(this);
    private RecyclerView rv_distance;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home_nearby_distance_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        rv_distance = bindView(R.id.rv_distance);
        rv_distance.setLayoutManager(new LinearLayoutManager(getActivity()));
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
    }

    @Override
    protected void loadData() {
        HomeNearbyDistanceRvAdapter adapter = new HomeNearbyDistanceRvAdapter(R.layout.item_home_nearby_distance, list, getActivity());
        rv_distance.setAdapter(adapter);
    }
}
