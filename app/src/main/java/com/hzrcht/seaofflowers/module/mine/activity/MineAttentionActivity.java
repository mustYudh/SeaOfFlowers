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
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;

public class MineAttentionActivity extends BaseBarActivity implements MineAttentionViewer {
    private int page = 1;
    private int pageSize = 10;
    @PresenterLifeCycle
    private MineAttentionPresenter mPresenter = new MineAttentionPresenter(this);
    private RecyclerView mAttention;
    private MineAttentionRvAdapter adapter;
    private List<HomeAttentionBean.RowsBean> list = new ArrayList<>();

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_attention);
    }

    @Override
    protected void loadData() {
        setTitle("我的关注");

        mAttention = bindView(R.id.rv_attention);
        mAttention.setLayoutManager(new LinearLayoutManager(getActivity()));


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
        if (homeAttentionBean != null) {
            if (homeAttentionBean.rows != null && homeAttentionBean.rows.size() != 0) {
                if (page > 1) {

                } else {
                    list.clear();
                }
                list.addAll(homeAttentionBean.rows);
                adapter = new MineAttentionRvAdapter(R.layout.item_home_attention, list, getActivity());
                mAttention.setAdapter(adapter);

                adapter.setOnItemCheckListener(new MineAttentionRvAdapter.OnItemCheckListener() {
                    @Override
                    public void setOnItemCheckClick(String id) {
                        Bundle bundle = new Bundle();
                        bundle.putString("USER_ID", id);
                        getLaunchHelper().startActivity(MinePersonalInfoActivity.class, bundle);
                    }
                });
                bindView(R.id.ll_empty, false);
                bindView(R.id.rv_attention, true);
            } else {
                if (page > 1) {

                } else {
                    bindView(R.id.ll_empty, true);
                    bindView(R.id.rv_attention, false);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getAttentionList(page, pageSize);

    }
}
