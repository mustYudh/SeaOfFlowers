package com.hzrcht.seaofflowers.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.home.adapter.HomeAttentionRvAdapter;
import com.hzrcht.seaofflowers.module.home.bean.HomeAttentionBean;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeAttentionPresenter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeAttentionViewer;
import com.yu.common.mvp.PresenterLifeCycle;



public class HomeAttentionFragment extends BaseFragment implements HomeAttentionViewer {
    private int page = 1;
    private int pageSize = 10;
    @PresenterLifeCycle
    private HomeAttentionPresenter mPresenter = new HomeAttentionPresenter(this);
    private RecyclerView mAttention;

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

        mAttention = bindView(R.id.rv_home_attention);
        mAttention.setLayoutManager(new LinearLayoutManager(getActivity()));


        mPresenter.getAttentionList(page, pageSize);
    }


    @Override
    public void getAttentionListSuccess(HomeAttentionBean homeAttentionBean) {
        if (homeAttentionBean != null) {
            if (homeAttentionBean.rows != null && homeAttentionBean.rows.size() != 0) {
                HomeAttentionRvAdapter adapter = new HomeAttentionRvAdapter(R.layout.item_home_attention, homeAttentionBean.rows, getActivity());
                mAttention.setAdapter(adapter);
            }
        }
    }
}
