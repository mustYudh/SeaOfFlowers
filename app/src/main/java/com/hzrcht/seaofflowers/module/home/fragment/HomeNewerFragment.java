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
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class HomeNewerFragment extends BaseFragment implements HomeNewrViewer {
    private int page = 1;
    private int pageSize = 10;
    private RecyclerView mAnchor;
    @PresenterLifeCycle
    private HomeNewrPresenter mPresenter = new HomeNewrPresenter(this);
    private HomeNewerRvAdapter adapter;
    private List<HomeAnchorListBean.RowsBean> list = new ArrayList<>();

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
        mAnchor.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAnchor.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 5, 2));

        mPresenter.getAnchorList("2", page, pageSize);
    }

    @Override
    public void getAnchorListSuccess(HomeAnchorListBean homeAnchorListBean) {
        if (homeAnchorListBean != null) {
            if (homeAnchorListBean.rows != null && homeAnchorListBean.rows.size() != 0) {
                if (page > 1) {

                } else {
                    list.clear();
                }
                list.addAll(homeAnchorListBean.rows);
                adapter = new HomeNewerRvAdapter(R.layout.item_home_limit, list, getActivity());
                mAnchor.setAdapter(adapter);
            }
        }
    }
}
