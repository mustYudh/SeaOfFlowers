package com.hzrcht.seaofflowers.module.home.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.home.adapter.HomeNearbyPageAdapter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeNearbyPresenter;
import com.hzrcht.seaofflowers.module.home.fragment.presenter.HomeNearbyViewer;
import com.hzrcht.seaofflowers.module.testFragment.TestFragment1;
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


public class HomeNearbyFragment extends BaseFragment implements HomeNearbyViewer {
    private List<String> mDataList = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();
    private ViewPager mViewPager;

    @PresenterLifeCycle
    private HomeNearbyPresenter mPresenter = new HomeNearbyPresenter(this);

    public static HomeNearbyFragment newInstance(int home_type) {
        HomeNearbyFragment newFragment = new HomeNearbyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("HOME_TYPE", home_type);
        newFragment.setArguments(bundle);
        return newFragment;
    }


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home_nearby_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        mViewPager = bindView(R.id.view_pager);
        mDataList.clear();
        mDataList.add("距离");
        mDataList.add("地图");

        fragments.clear();
        fragments.add(new HomeNearbyDistanceFragment());
        fragments.add(new TestFragment1());

        initMagicIndicator();

        Log.d("aaaa", "aaaaa走了吗");

    }

    /**
     * 初始化tablayout
     */
    private void initMagicIndicator() {
        HomeNearbyPageAdapter adapter = new HomeNearbyPageAdapter(getChildFragmentManager(), mDataList, fragments);
        mViewPager.setAdapter(adapter);
        MagicIndicator magicIndicator = bindView(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                commonPagerTitleView.setContentView(R.layout.item_top_tab_nearby);
                // 初始化
                final TextView titleText = commonPagerTitleView.findViewById(R.id.tv_title);
                titleText.setText(mDataList.get(index));

                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleText.setTextColor(Color.parseColor("#FFFFFF"));
                        titleText.setBackground(getResources().getDrawable(R.drawable.shape_nearby_select));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleText.setTextColor(Color.parseColor("#666666"));
                        titleText.setBackground(getResources().getDrawable(R.drawable.shape_nearby_normal));
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
                return null;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);

    }
}
