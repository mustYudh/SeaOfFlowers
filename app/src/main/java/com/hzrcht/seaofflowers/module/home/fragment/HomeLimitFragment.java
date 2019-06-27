package com.hzrcht.seaofflowers.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.home.adapter.BannerViewHolder;
import com.hzrcht.seaofflowers.module.home.adapter.HomeLimitRvAdapter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeLimitPresenter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeLimitViewer;
import com.hzrcht.seaofflowers.module.view.ScreenSpaceItemDecoration;
import com.yu.common.mvp.PresenterLifeCycle;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;


public class HomeLimitFragment extends BaseFragment implements HomeLimitViewer {
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    private HomeLimitPresenter mPresenter = new HomeLimitPresenter(this);
    private MZBannerView mBanner;

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
        for (int i = 0; i < 10; i++) {
            list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561634541402&di=dd1d43b504422b022c7e6c057786bfbc&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20180611%2F88957964ee86464d8589b722e874f5ed.jpeg");
        }
        RecyclerView rv_home = bindView(R.id.rv_home);
        mBanner = bindView(R.id.banner);
        rv_home.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv_home.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 5, 2));
        HomeLimitRvAdapter adapter = new HomeLimitRvAdapter(R.layout.item_home_limit, list, getActivity());
        rv_home.setAdapter(adapter);
        initBanner(list);
    }


    /**
     * banner 初始化
     *
     * @param xbanner
     */
    private void initBanner(List<String> xbanner) {
        String json = new Gson().toJson(xbanner);
        Gson gson = new Gson();

        if (mBanner != null) {
            mBanner.setDuration(500);
            mBanner.setDelayedTime(3000);
            mBanner.setCanLoop(true);
            mBanner.setPages(list, new MZHolderCreator() {
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
}
