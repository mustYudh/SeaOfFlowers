package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.home.bean.HomeAttentionBean;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineAttentionPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineAttentionViewer;
import com.hzrcht.seaofflowers.module.mine.adapter.MineAttentionRvAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

public class MineAttentionActivity extends BaseBarActivity implements MineAttentionViewer {
    private int page = 1;
    private int pageSize = 10;
    @PresenterLifeCycle
    private MineAttentionPresenter mPresenter = new MineAttentionPresenter(this);
    private RecyclerView mAttention;
    private MineAttentionRvAdapter adapter;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_attention);
    }

    @Override
    protected void loadData() {
        setTitle("我的关注");

        refreshLayout = bindView(R.id.refreshLayout);
        mAttention = bindView(R.id.rv_attention);
        mAttention.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new MineAttentionRvAdapter(R.layout.item_home_attention, getActivity());
        mAttention.setAdapter(adapter);

        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setEnableOverScrollBounce(false);
        refreshLayout.setEnableAutoLoadMore(false);

        mPresenter.getAttentionList(page, pageSize);

        refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            int i = 1;
            page += i;
            mPresenter.getAttentionList(page, pageSize);
        });
        refreshLayout.setOnRefreshListener(refreshLayout12 -> {
            page = 1;
            mPresenter.getAttentionList(page, pageSize);
        });

        bindView(R.id.action_back, view -> {
            setResult(1);
            finish();
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(1);
            finish();
        }
        return true;
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
            adapter.setOnItemCheckListener(id -> {
                Bundle bundle = new Bundle();
                bundle.putString("USER_ID", id);
                getLaunchHelper().startActivity(MinePersonalInfoActivity.class, bundle);
            });
            bindView(R.id.ll_empty, false);
            bindView(R.id.rv_attention, true);
        } else {
            if (page > 1) {
                ToastUtils.show("没有更多了");
            } else {
                bindView(R.id.ll_empty, true);
                bindView(R.id.rv_attention, false);
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
