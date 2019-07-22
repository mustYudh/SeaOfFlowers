package com.hzrcht.seaofflowers.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.home.adapter.BannerViewHolder;
import com.hzrcht.seaofflowers.module.home.adapter.HomeLimitRvAdapter;
import com.hzrcht.seaofflowers.module.home.bean.HomeAnchorListBean;
import com.hzrcht.seaofflowers.module.home.bean.HomeBannerBean;
import com.hzrcht.seaofflowers.module.home.bean.MineLocationAnchorBean;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeLimitPresenter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeLimitViewer;
import com.hzrcht.seaofflowers.module.view.ScreenSpaceItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.yu.common.glide.ImageLoader;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class HomeLimitFragment extends BaseFragment implements HomeLimitViewer {
    @PresenterLifeCycle
    private HomeLimitPresenter mPresenter = new HomeLimitPresenter(this);
    private MZBannerView mBanner;
    private int home_type;
    private int page = 1;
    private int pageSize = 10;
    private RecyclerView mAnchor;
    private HomeLimitRvAdapter adapter;
    private Disposable subscribe;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home_limit_view;
    }

    public static HomeLimitFragment newInstance(int home_type) {
        HomeLimitFragment newFragment = new HomeLimitFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("HOME_TYPE", home_type);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            home_type = bundle.getInt("HOME_TYPE");
        }

        mAnchor = bindView(R.id.rv_home);
        mBanner = bindView(R.id.banner);
        refreshLayout = bindView(R.id.refreshLayout);
        mAnchor.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAnchor.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 5, 2));

        if (home_type == 2) {
            mBanner.setVisibility(View.GONE);
        } else {
            mBanner.setVisibility(View.VISIBLE);
            mPresenter.getBannerList();
        }

        adapter = new HomeLimitRvAdapter(getActivity());
        mAnchor.setAdapter(adapter);
        mPresenter.getAnchorList(home_type + "", page, pageSize);

        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setEnableOverScrollBounce(false);
        refreshLayout.setEnableAutoLoadMore(false);

        refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            int i = 1;
            page += i;
            mPresenter.getAnchorList(home_type + "", page, pageSize);

        });
        refreshLayout.setOnRefreshListener(refreshLayout12 -> {
            page = 1;
            mPresenter.getAnchorList(home_type + "", page, pageSize);
        });
    }


    /**
     * banner 初始化
     *
     * @param xbanner
     */
    private void initBanner(List<HomeBannerBean.RowsBean> xbanner) {
        String json = new Gson().toJson(xbanner);
        Gson gson = new Gson();

        if (mBanner != null) {
            mBanner.setDuration(500);
            mBanner.setDelayedTime(3000);
            mBanner.setCanLoop(true);
            mBanner.setPages(xbanner, new MZHolderCreator() {
                @Override
                public MZViewHolder createViewHolder() {
                    return new BannerViewHolder();
                }
            });
            mBanner.start();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mBanner != null) {
            mBanner.start();//开始轮播
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBanner != null) {
            mBanner.pause();//暂停轮播
        }
    }

    @Override
    public void onDestroy() {
        if (mBanner != null) {
            mBanner.pause();//暂停轮播
        }
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
            subscribe = null;
        }
        super.onDestroy();
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
            List<MineLocationAnchorBean> list = new ArrayList<>();
            if (page > 1) {

            } else {
                MineLocationAnchorBean mineLocationAnchorTopBean = new MineLocationAnchorBean();
                mineLocationAnchorTopBean.pair = homeAnchorListBean.pair;
                mineLocationAnchorTopBean.itemType = 0;
                list.add(mineLocationAnchorTopBean);
            }

            for (int i = 0; i < homeAnchorListBean.rows.size(); i++) {
                MineLocationAnchorBean mineLocationAnchorBean = new MineLocationAnchorBean();
                HomeAnchorListBean.RowsBean rowsBean = homeAnchorListBean.rows.get(i);
                mineLocationAnchorBean.id = rowsBean.id;
                mineLocationAnchorBean.nick_name = rowsBean.nick_name;
                mineLocationAnchorBean.cover = rowsBean.cover;
                mineLocationAnchorBean.sex = rowsBean.sex;
                mineLocationAnchorBean.age = rowsBean.age;
                mineLocationAnchorBean.sign = rowsBean.sign;
                mineLocationAnchorBean.work = rowsBean.work;
                mineLocationAnchorBean.video_amount = rowsBean.video_amount;
                mineLocationAnchorBean.online_type = rowsBean.online_type;
                mineLocationAnchorBean.itemType = 1;
                list.add(mineLocationAnchorBean);
            }

            if (page > 1) {
                adapter.addData(list);
            } else {
                adapter.setNewData(list);
            }

            adapter.setOnItemDetailsDoCilckListener((pair, imageView) -> subscribe = Observable.interval(0, 10, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .doOnNext(aLong -> {
                        getActivity().runOnUiThread(() -> ImageLoader.getInstance().displayImage(imageView, pair.get((int) (Math.random() * (pair.size()))), R.drawable.ic_placeholder, R.drawable.ic_placeholder_error));
                    })
                    .subscribe());
            bindView(R.id.ll_empty, false);
            bindView(R.id.rl_home, true);
        } else {
            if (page > 1) {
                ToastUtils.show("没有更多了");
            } else {
                bindView(R.id.ll_empty, true);
                bindView(R.id.rl_home, false);
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

    @Override
    public void getBannerListSuccess(HomeBannerBean homeBannerBean) {
        if (homeBannerBean != null) {
            if (homeBannerBean.rows != null && homeBannerBean.rows.size() != 0) {
                initBanner(homeBannerBean.rows);
            }
        }
    }
}
