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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;


public class HomeAttentionFragment extends BaseFragment implements HomeAttentionViewer {
    private int page = 1;
    private int pageSize = 10;
    @PresenterLifeCycle
    private HomeAttentionPresenter mPresenter = new HomeAttentionPresenter(this);
    private RecyclerView mAttention;
    private HomeAttentionRvAdapter adapter;
    private SmartRefreshLayout refreshLayout;

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
        refreshLayout = bindView(R.id.refreshLayout);
        mAttention = bindView(R.id.rv_home_attention);
        mAttention.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HomeAttentionRvAdapter(R.layout.item_home_attention, getActivity());
        mAttention.setAdapter(adapter);

        mPresenter.getAttentionList(page, pageSize);

        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setEnableOverScrollBounce(false);
        refreshLayout.setEnableAutoLoadMore(false);

        refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            int i = 1;
            page += i;
            mPresenter.getAttentionList(page, pageSize);

        });
        refreshLayout.setOnRefreshListener(refreshLayout12 -> {
            page = 1;
            mPresenter.getAttentionList(page, pageSize);
        });
    }


    @Override
    public void getAttentionListSuccess(HomeAttentionBean homeAttentionBean) {
        if (refreshLayout != null) {
            if (page > 1) {
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishRefresh();
            }
        }
        if (homeAttentionBean != null && homeAttentionBean.rows != null && homeAttentionBean.rows.size() != 0) {
            if (page > 1) {
                adapter.addData(homeAttentionBean.rows);
            } else {
                adapter.setNewData(homeAttentionBean.rows);
            }
            bindView(R.id.ll_empty, false);
            bindView(R.id.rv_home_attention, true);
        } else {
            if (page > 1) {
                ToastUtils.show("没有更多了");
            } else {
                bindView(R.id.ll_empty, true);
                bindView(R.id.rv_home_attention, false);
            }
        }
    }

    @Override
    public void getAttentionListFail() {
        if (refreshLayout != null) {
            if (page > 1) {
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishRefresh();
            }
        }
    }
}
