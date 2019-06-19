package com.hzrcht.seaofflowers.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.home.adapter.HomeLimitRvAdapter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeLimitPresenter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeLimitViewer;
import com.hzrcht.seaofflowers.module.view.ScreenSpaceItemDecoration;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class HomeLimitFragment extends BaseFragment implements HomeLimitViewer {
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    private HomeLimitPresenter mPresenter = new HomeLimitPresenter(this);

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home_limit_view;
    }

    public static HomeLimitFragment newInstance(int home_type) {
        HomeLimitFragment newFragment = new HomeLimitFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("HOME_TYPE", home_type);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        RecyclerView rv_home = bindView(R.id.rv_home);
        rv_home.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv_home.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 5));
        HomeLimitRvAdapter adapter = new HomeLimitRvAdapter(R.layout.item_home_limit, list, getActivity());
        rv_home.setAdapter(adapter);
    }
}
