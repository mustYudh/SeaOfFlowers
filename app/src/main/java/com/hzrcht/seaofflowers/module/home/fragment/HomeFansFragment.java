package com.hzrcht.seaofflowers.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.home.adapter.HomeFansRvAdapter;
import com.hzrcht.seaofflowers.module.home.bean.HomeFansBean;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeFansPresenter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeFansViewer;
import com.hzrcht.seaofflowers.module.mine.activity.TRTCMainActivity;
import com.hzrcht.seaofflowers.module.mine.activity.bean.LiveStartBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;


public class HomeFansFragment extends BaseFragment implements HomeFansViewer {
    @PresenterLifeCycle
    private HomeFansPresenter mPresenter = new HomeFansPresenter(this);
    private RecyclerView rv_home_fans;
    private HomeFansRvAdapter adapter;
    private SmartRefreshLayout refreshLayout;


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home_fans_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    public static HomeFansFragment newInstance(int home_type) {
        HomeFansFragment newFragment = new HomeFansFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("HOME_TYPE", home_type);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    protected void loadData() {
        rv_home_fans = bindView(R.id.rv_home_fans);
        refreshLayout = bindView(R.id.refreshLayout);
        rv_home_fans.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HomeFansRvAdapter(R.layout.item_home_fans, getActivity());
        rv_home_fans.setAdapter(adapter);
        mPresenter.getFansList();

        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setEnableOverScrollBounce(false);
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setEnableLoadMore(true);


        refreshLayout.setOnRefreshListener(refreshLayout12 -> {
            mPresenter.getFansList();
        });
    }

    @Override
    public void getFansListSuccess(HomeFansBean homeFansBean) {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
        }
        if (homeFansBean != null && homeFansBean.rows != null && homeFansBean.rows.size() != 0) {
            adapter.setNewData(homeFansBean.rows);

            adapter.setOnItemVideoListener(item -> mPresenter.liveStart(item.id + "", item));

            bindView(R.id.rv_home_fans, true);
            bindView(R.id.ll_empty, false);
        } else {
            bindView(R.id.rv_home_fans, false);
            bindView(R.id.ll_empty, true);
        }
    }

    @Override
    public void getFansListFail() {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
        }
    }

    @Override
    public void liveStartSuccess(LiveStartBean liveStartBean, HomeFansBean.RowBean item) {
        if (liveStartBean != null) {
            Bundle bundleVideo = new Bundle();
            bundleVideo.putString("USER_ID", item.id + "");
            bundleVideo.putString("HEAD_IMG", item.head_img + "");
            bundleVideo.putString("NICK_NAME", item.nick_name + "");
            bundleVideo.putString("USER_AGE", item.age + "");
            bundleVideo.putString("IS_ATTENT", "0");
            bundleVideo.putString("LIVE_ID", liveStartBean.live_id + "");
            bundleVideo.putString("TYPE_IN", "1");
            getLaunchHelper().startActivity(TRTCMainActivity.class, bundleVideo);
        } else {
            ToastUtils.show("开启视频出错!");
        }
    }
}
