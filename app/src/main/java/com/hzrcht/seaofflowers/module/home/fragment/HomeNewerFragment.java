package com.hzrcht.seaofflowers.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.home.adapter.HomeNewerRvAdapter;
import com.hzrcht.seaofflowers.module.home.bean.HomeAnchorListBean;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeNewrPresenter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeNewrViewer;
import com.hzrcht.seaofflowers.module.view.ScreenSpaceItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;


public class HomeNewerFragment extends BaseFragment implements HomeNewrViewer {
    private int page = 1;
    private int pageSize = 10;
    private RecyclerView mAnchor;
    @PresenterLifeCycle
    private HomeNewrPresenter mPresenter = new HomeNewrPresenter(this);
    private HomeNewerRvAdapter adapter;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home_newer_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        mAnchor = bindView(R.id.rv_home);
        refreshLayout = bindView(R.id.refreshLayout);
        mAnchor.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAnchor.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 5, 2));

        adapter = new HomeNewerRvAdapter(R.layout.item_home_limit, getActivity());
        mAnchor.setAdapter(adapter);

        mPresenter.getAnchorList("2", page, pageSize);

        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setEnableOverScrollBounce(false);
        refreshLayout.setEnableAutoLoadMore(false);

        refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            int i = 1;
            page += i;
            mPresenter.getAnchorList("2", page, pageSize);

        });
        refreshLayout.setOnRefreshListener(refreshLayout12 -> {
            page = 1;
            mPresenter.getAnchorList("2", page, pageSize);
        });
    }

    @Override
    public void getAnchorListSuccess(HomeAnchorListBean homeAnchorListBean) {
        if (refreshLayout != null) {
            if (page > 1) {
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishRefresh();
            }
        }
        if (homeAnchorListBean != null && homeAnchorListBean.rows != null && homeAnchorListBean.rows.size() != 0) {

            if (page > 1) {
                adapter.addData(homeAnchorListBean.rows);
            } else {
                adapter.setNewData(homeAnchorListBean.rows);
            }

            bindView(R.id.ll_empty, false);
            bindView(R.id.rv_home, true);
        } else {
            if (page > 1) {
                ToastUtils.show("没有更多了");
            } else {
                bindView(R.id.ll_empty, true);
                bindView(R.id.rv_home, false);
            }
        }
    }

    @Override
    public void getAnchorListFail() {
        if (refreshLayout != null) {
            if (page > 1) {
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishRefresh();
            }
        }
    }
}
