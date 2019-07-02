package com.hzrcht.seaofflowers.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeNearbyMapPresenter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeNearbyMapViewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class HomeNearbyMapFragment extends BaseFragment implements HomeNearbyMapViewer {

    @PresenterLifeCycle
    private HomeNearbyMapPresenter mPresenter = new HomeNearbyMapPresenter(this);

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home_nearby_map_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {

    }
}
