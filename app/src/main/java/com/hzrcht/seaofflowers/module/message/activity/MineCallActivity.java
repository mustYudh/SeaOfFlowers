package com.hzrcht.seaofflowers.module.message.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.message.activity.presenter.MineCallPresenter;
import com.hzrcht.seaofflowers.module.message.activity.presenter.MineCallViewer;
import com.hzrcht.seaofflowers.module.message.adapter.MineCallRvAdapter;
import com.hzrcht.seaofflowers.module.mine.bean.MineCallBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;


public class MineCallActivity extends BaseBarActivity implements MineCallViewer {
    @PresenterLifeCycle
    private MineCallPresenter mPresenter = new MineCallPresenter(this);
    private MineCallRvAdapter adapter;
    private RecyclerView rv_mine_call;
    private int page = 1;
    private int pageSize = 10;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_call_view);
    }

    @Override
    protected void loadData() {
        setTitle("我的通话");
        refreshLayout = bindView(R.id.refreshLayout);
        rv_mine_call = bindView(R.id.rv_mine_call);
        rv_mine_call.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new MineCallRvAdapter(R.layout.item_mine_call, getActivity());
        rv_mine_call.setAdapter(adapter);

        mPresenter.getLiveList(page, pageSize);

        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setEnableOverScrollBounce(false);
        refreshLayout.setEnableAutoLoadMore(false);

        refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            int i = 1;
            page += i;
            mPresenter.getLiveList(page, pageSize);

        });
        refreshLayout.setOnRefreshListener(refreshLayout12 -> {
            page = 1;
            mPresenter.getLiveList(page, pageSize);
        });
    }

    @Override
    public void getLiveListSuccess(MineCallBean mineCallBean) {
        if (refreshLayout != null) {
            if (page > 1) {
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishRefresh();
            }
        }
        if (mineCallBean != null && mineCallBean.rows != null && mineCallBean.rows.size() != 0) {

            if (page > 1) {
                adapter.addData(mineCallBean.rows);
            } else {
                adapter.setNewData(mineCallBean.rows);
            }

            bindView(R.id.ll_empty, false);
            bindView(R.id.rv_mine_call, true);
        } else {

            if (page > 1) {
                ToastUtils.show("没有更多了");
            } else {
                bindView(R.id.ll_empty, true);
                bindView(R.id.rv_mine_call, false);
            }
        }
    }

    @Override
    public void getLiveListFail() {
        if (refreshLayout != null) {
            if (page > 1) {
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishRefresh();
            }
        }
    }
}
