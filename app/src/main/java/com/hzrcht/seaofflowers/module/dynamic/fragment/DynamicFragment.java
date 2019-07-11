package com.hzrcht.seaofflowers.module.dynamic.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarFragment;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.data.UserProfile;
import com.hzrcht.seaofflowers.module.dynamic.activity.ReleaseDynamicActivity;
import com.hzrcht.seaofflowers.module.dynamic.fragment.presenter.DynamicPresenter;
import com.hzrcht.seaofflowers.module.dynamic.fragment.presenter.DynamicViewer;
import com.hzrcht.seaofflowers.module.home.adapter.HomePageAdapter;
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


public class DynamicFragment extends BaseBarFragment implements DynamicViewer {
    @PresenterLifeCycle
    private DynamicPresenter mPresenter = new DynamicPresenter(this);
    private List<String> mDataList = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();
    private ViewPager mViewPager;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_dynamic_view;
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.fragment_dynamic_view_bar;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        bindView(R.id.ll_add, UserProfile.getInstance().getAnchorType() == 1);
        bindView(R.id.ll_add, view -> {
            getLaunchHelper().startActivity(ReleaseDynamicActivity.class);
        });
        mViewPager = bindView(R.id.view_pager);

        mDataList.add("推荐");
        mDataList.add("关注");

        fragments.add(new RecommendDynamicFragment());
        fragments.add(new AttentionDynamicFragment());
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
        commonNavigator.setAdjustMode(false);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                commonPagerTitleView.setContentView(R.layout.item_top_tab_dynamic);
                // 初始化
                final TextView titleText = commonPagerTitleView.findViewById(R.id.tv_title);
                titleText.setText(mDataList.get(index));

                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleText.setTextSize(20);
                        titleText.setTextColor(Color.parseColor("#FFFFFF"));
                        titleText.setTypeface(Typeface.DEFAULT_BOLD);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleText.setTextSize(16);
                        titleText.setTextColor(Color.parseColor("#FFFFFF"));
                        titleText.setTypeface(Typeface.DEFAULT);
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
    }
}
