package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.home.bean.HomeAttentionBean;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineAttentionPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineAttentionViewer;
import com.hzrcht.seaofflowers.module.mine.adapter.MineAttentionRvAdapter;
import com.yu.common.mvp.PresenterLifeCycle;

public class MineAttentionActivity extends BaseBarActivity implements MineAttentionViewer {
    private int page = 1;
    private int pageSize = 10;
    @PresenterLifeCycle
    private MineAttentionPresenter mPresenter = new MineAttentionPresenter(this);
    private RecyclerView mAttention;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_attention);
    }

    @Override
    protected void loadData() {
        setTitle("我的关注");

        mAttention = bindView(R.id.rv_attention);
        mAttention.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPresenter.getAttentionList(page, pageSize);
    }

    @Override
    public void getAttentionListSuccess(HomeAttentionBean homeAttentionBean) {
        if (homeAttentionBean != null) {
            if (homeAttentionBean.rows != null && homeAttentionBean.rows.size() != 0) {
                MineAttentionRvAdapter adapter = new MineAttentionRvAdapter(R.layout.item_home_attention, homeAttentionBean.rows, getActivity());
                mAttention.setAdapter(adapter);
            }
        }
    }
}
