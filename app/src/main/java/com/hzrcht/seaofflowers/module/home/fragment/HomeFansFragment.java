package com.hzrcht.seaofflowers.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.home.adapter.HomeFansRvAdapter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeFansPresenter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeFansViewer;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class HomeFansFragment extends BaseFragment implements HomeFansViewer {
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    private HomeFansPresenter mPresenter = new HomeFansPresenter(this);

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home_fans_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    public static HomeFansFragment newInstance(int home_type) {
        HomeFansFragment newFragment = new HomeFansFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("HOME_TYPE", home_type);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    protected void loadData() {
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        RecyclerView rv_home_fans = bindView(R.id.rv_home_fans);
        rv_home_fans.setLayoutManager(new LinearLayoutManager(getActivity()));
        HomeFansRvAdapter adapter = new HomeFansRvAdapter(R.layout.item_home_fans, list, getActivity());
        rv_home_fans.setAdapter(adapter);
    }
}
