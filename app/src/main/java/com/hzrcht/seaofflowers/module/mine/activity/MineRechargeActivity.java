package com.hzrcht.seaofflowers.module.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.home.adapter.HomePageAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineRechargePresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineRechargeViewer;
import com.hzrcht.seaofflowers.module.mine.fragment.DredgeVipFragment;
import com.hzrcht.seaofflowers.module.mine.fragment.RechargeCoinFragment;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
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


public class MineRechargeActivity extends BaseBarActivity implements MineRechargeViewer {
    private List<String> mDataList = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();
    private ViewPager mViewPager;
    @PresenterLifeCycle
    private MineRechargePresenter mPresenter = new MineRechargePresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_recharge_view);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_mine_recharge_view_bar;
    }

    @Override
    protected void loadData() {
        Bundle bundle = getIntent().getExtras();
        int type = bundle.getInt("TYPE", 0);
        mViewPager = bindView(R.id.view_pager);

        mDataList.add("充值金币");
        mDataList.add("开通VIP");

        fragments.add(new RechargeCoinFragment());
        fragments.add(new DredgeVipFragment());
        initMagicIndicator(type);

        //客服
        bindView(R.id.tv_online_service, view -> {
            try {
                //跳转到添加好友，如果qq号是好友了，直接聊天
                String url = "mqqwpa://im/chat?chat_type=wpa&uin=2592974828";//uin是发送过去的qq号码
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.show("请检查是否安装QQ");
            }
        });
        bindView(R.id.action_back, view -> {
            setResult(1);
            finish();
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(1);
            finish();
        }
        return true;
    }

    /**
     * 初始化tablayout
     */
    private void initMagicIndicator(int type) {
        HomePageAdapter adapter = new HomePageAdapter(getSupportFragmentManager(), mDataList, fragments);
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
                commonPagerTitleView.setContentView(R.layout.item_top_tab_recharge);
                // 初始化
                final TextView titleText = commonPagerTitleView.findViewById(R.id.tv_title);
                titleText.setText(mDataList.get(index));

                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleText.setTextSize(20);
                        titleText.setTextColor(Color.parseColor("#000000"));
                        titleText.setTypeface(Typeface.DEFAULT_BOLD);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleText.setTextSize(16);
                        titleText.setTextColor(Color.parseColor("#000000"));
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
                linePagerIndicator.setColors(Color.parseColor("#9897E7"));
                linePagerIndicator.setLineWidth(DensityUtils.dp2px(getActivity(), 20));
                linePagerIndicator.setLineHeight(DensityUtils.dp2px(getActivity(), 3));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);

        mViewPager.setCurrentItem(type);
    }
}
