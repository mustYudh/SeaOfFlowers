package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MinePresentRvAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.bean.GiftListBean;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MinePresentPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MinePresentViewer;
import com.hzrcht.seaofflowers.module.view.ScreenSpaceItemDecoration;
import com.yu.common.mvp.PresenterLifeCycle;


public class MinePresentActivity extends BaseBarActivity implements MinePresentViewer {
    @PresenterLifeCycle
    private MinePresentPresenter mPresenter = new MinePresentPresenter(this);
    private RecyclerView mPresent;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_present_view);
    }

    @Override
    protected void loadData() {
        setTitle("礼物柜");
        Bundle bundle = getIntent().getExtras();
        String user_id = bundle.getString("USER_ID");
        mPresent = bindView(R.id.rv_present);
        mPresent.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mPresent.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 10, 4));

        mPresenter.getGiftIndex(user_id);
    }

    @Override
    public void getGiftIndexSuccess(GiftListBean giftListBean) {
        if (giftListBean != null) {
            if (giftListBean.rows != null && giftListBean.rows.size() != 0) {
                MinePresentRvAdapter adapter = new MinePresentRvAdapter(R.layout.item_mine_present, giftListBean.rows, getActivity());
                mPresent.setAdapter(adapter);
                bindText(R.id.tv_count, giftListBean.rows.size() + "");
            } else {
                bindText(R.id.tv_count, "0");
            }
        }
    }
}
