package com.hzrcht.seaofflowers.module.home.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.home.adapter.HomePageAdapter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeFragmentPresenter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeFragmentViewer;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.utils.DensityUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment implements HomeFragmentViewer {
    private List<String> mDataList = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();
    private ViewPager mViewPager;

    @PresenterLifeCycle
    private HomeFragmentPresenter mPresenter = new HomeFragmentPresenter(this);

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        mDataList.add("关注");
        mDataList.add("推荐");
        mDataList.add("新人");
        mDataList.add("附近");
        mDataList.add("视频");
        mViewPager = bindView(R.id.view_pager);


        for (int i = 0; i < mDataList.size(); i++) {
            fragments.add(HomeLimitFragment.newInstance(i));
        }
        initMagicIndicator();
    }

    /**
     * 初始化tablayout
     */
    private void initMagicIndicator() {
        HomePageAdapter adapter = new HomePageAdapter(getChildFragmentManager(), mDataList, fragments);
        mViewPager.setAdapter(adapter);
        MagicIndicator magicIndicator = bindView(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                commonPagerTitleView.setContentView(R.layout.item_top_tab_list);
                // 初始化
                final TextView titleText = commonPagerTitleView.findViewById(R.id.tv_title);
                titleText.setText(mDataList.get(index));

                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleText.setTextSize(20);
                        titleText.setTextColor(Color.parseColor("#FFFFFF"));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleText.setTextSize(16);
                        titleText.setTextColor(Color.parseColor("#FFFFFF"));
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
                    }
                });
                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setColors(Color.parseColor("#FFFFFF"));
                linePagerIndicator.setLineWidth(DensityUtils.dp2px(getActivity(), 20));
                linePagerIndicator.setLineHeight(DensityUtils.dp2px(getActivity(), 3));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);

        mViewPager.setCurrentItem(1);
    }
}
