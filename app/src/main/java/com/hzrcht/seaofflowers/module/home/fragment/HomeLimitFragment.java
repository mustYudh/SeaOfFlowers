package com.hzrcht.seaofflowers.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.home.adapter.BannerViewHolder;
import com.hzrcht.seaofflowers.module.home.adapter.HomeLimitRvAdapter;
import com.hzrcht.seaofflowers.module.home.bean.AnchorListBean;
import com.hzrcht.seaofflowers.module.home.bean.HomeBannerBean;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeLimitPresenter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeLimitViewer;
import com.hzrcht.seaofflowers.module.view.ScreenSpaceItemDecoration;
import com.yu.common.mvp.PresenterLifeCycle;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.List;


public class HomeLimitFragment extends BaseFragment implements HomeLimitViewer {
    @PresenterLifeCycle
    private HomeLimitPresenter mPresenter = new HomeLimitPresenter(this);
    private MZBannerView mBanner;
    private int home_type;
    private RecyclerView mAnchor;

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
        mAnchor.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAnchor.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 5, 2));

        Log.e("aaaa", "走了吗");


        mPresenter.getAnchorList(home_type + "");
        mPresenter.getBannerList();
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
        super.onDestroy();
        if (mBanner != null) {
            mBanner.pause();//暂停轮播
        }
    }

    @Override
    public void getAnchorListSuccess(AnchorListBean anchorListBean) {
        if (anchorListBean != null) {
            if (anchorListBean.rows != null && anchorListBean.rows.size() != 0) {

                HomeLimitRvAdapter adapter = new HomeLimitRvAdapter(R.layout.item_home_limit, anchorListBean.rows, getActivity());
                mAnchor.setAdapter(adapter);
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
