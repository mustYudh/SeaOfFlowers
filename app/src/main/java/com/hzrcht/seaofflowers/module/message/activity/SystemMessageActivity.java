package com.hzrcht.seaofflowers.module.message.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.message.activity.presenter.SystemMessagePresenter;
import com.hzrcht.seaofflowers.module.message.activity.presenter.SystemMessageViewer;
import com.hzrcht.seaofflowers.module.message.adapter.SystemMessageRvAdapter;
import com.hzrcht.seaofflowers.module.message.bean.UserSysMessageBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;


public class SystemMessageActivity extends BaseBarActivity implements SystemMessageViewer {
    @PresenterLifeCycle
    private SystemMessagePresenter mPresenter = new SystemMessagePresenter(this);
    private SystemMessageRvAdapter adapter;
    private int page = 1;
    private int pageSize = 10;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView rv_system_message;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_system_message_view);
    }

    @Override
    protected void loadData() {
        setTitle("系统消息");
        refreshLayout = bindView(R.id.refreshLayout);
        rv_system_message = bindView(R.id.rv_system_message);
        rv_system_message.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SystemMessageRvAdapter(R.layout.item_system_message, getActivity());
        rv_system_message.setAdapter(adapter);
        mPresenter.getUserSysMessage(page, pageSize);

        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setEnableOverScrollBounce(false);
        refreshLayout.setEnableAutoLoadMore(false);

        refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            int i = 1;
            page += i;
            mPresenter.getUserSysMessage(page, pageSize);

        });
        refreshLayout.setOnRefreshListener(refreshLayout12 -> {
            page = 1;
            mPresenter.getUserSysMessage(page, pageSize);
        });

    }

    @Override
    public void getUserSysMessageSuccess(UserSysMessageBean userSysMessageBean) {
        if (refreshLayout != null) {
            if (page > 1) {
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishRefresh();
            }
        }
        if (userSysMessageBean != null && userSysMessageBean.rows != null && userSysMessageBean.rows.size() != 0) {
            if (page > 1) {
                adapter.addData(userSysMessageBean.rows);
            } else {
                adapter.setNewData(userSysMessageBean.rows);
            }

            bindView(R.id.ll_empty, false);
            bindView(R.id.rv_system_message, true);
        } else {
            if (page > 1) {
                ToastUtils.show("没有更多了");
            } else {
                bindView(R.id.ll_empty, true);
                bindView(R.id.rv_system_message, false);
            }
        }
    }

    @Override
    public void getUserSysMessageFail() {
        if (refreshLayout != null) {
            if (page > 1) {
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishRefresh();
            }
        }
    }
}
