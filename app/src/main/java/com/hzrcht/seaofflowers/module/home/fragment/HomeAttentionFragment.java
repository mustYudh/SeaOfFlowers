package com.hzrcht.seaofflowers.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.home.adapter.HomeAttentionRvAdapter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeAttentionPresenter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeAttentionViewer;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class HomeAttentionFragment extends BaseFragment implements HomeAttentionViewer {
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    private HomeAttentionPresenter mPresenter = new HomeAttentionPresenter(this);

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home_attention_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    public static HomeAttentionFragment newInstance(int home_type) {
        HomeAttentionFragment newFragment = new HomeAttentionFragment();
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
        RecyclerView rv_home_attention = bindView(R.id.rv_home_attention);
        rv_home_attention.setLayoutManager(new LinearLayoutManager(getActivity()));
        HomeAttentionRvAdapter adapter = new HomeAttentionRvAdapter(R.layout.item_home_attention, list, getActivity());
        rv_home_attention.setAdapter(adapter);
    }
}
