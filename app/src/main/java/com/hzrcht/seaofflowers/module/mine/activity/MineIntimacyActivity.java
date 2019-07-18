package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.bean.AnchorUserInfoBean;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineIntimacyPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineIntimacyViewer;
import com.hzrcht.seaofflowers.module.mine.adapter.MineIntimacyRvAdapter;
import com.yu.common.mvp.PresenterLifeCycle;

/**
 * 亲密榜
 */
public class MineIntimacyActivity extends BaseBarActivity implements MineIntimacyViewer {
    @PresenterLifeCycle
    private MineIntimacyPresenter mPresenter = new MineIntimacyPresenter(this);
    private RecyclerView rv_intimacy;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_intimacy_view);
    }

    @Override
    protected void loadData() {
        setTitle("亲密榜");
        Bundle bundle = getIntent().getExtras();
        String user_id = bundle.getString("USER_ID");
        rv_intimacy = bindView(R.id.rv_intimacy);
        rv_intimacy.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPresenter.getUserInfo(user_id);

    }

    @Override
    public void getUserInfoSuccess(AnchorUserInfoBean anchorUserInfoBean) {
        if (anchorUserInfoBean != null && anchorUserInfoBean.near != null && anchorUserInfoBean.near.size() != 0) {
            MineIntimacyRvAdapter adapter = new MineIntimacyRvAdapter(R.layout.item_mine_intimacy, anchorUserInfoBean.near, getActivity());
            rv_intimacy.setAdapter(adapter);
            bindView(R.id.rv_intimacy, true);
            bindView(R.id.ll_empty, false);
        } else {
            //空页面
            bindView(R.id.rv_intimacy, false);
            bindView(R.id.ll_empty, true);
        }
    }
}
