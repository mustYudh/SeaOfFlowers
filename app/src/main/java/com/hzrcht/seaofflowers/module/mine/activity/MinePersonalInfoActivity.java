package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseActivity;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineLocationDynamicBean;
import com.hzrcht.seaofflowers.module.home.adapter.BannerViewHolder;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MineInfoDataRvAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MineInfoDynamicRvAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MinePersonalInfoPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MinePersonalInfoViewer;
import com.hzrcht.seaofflowers.module.view.ScreenSpaceItemDecoration;
import com.hzrcht.seaofflowers.utils.DialogUtils;
import com.yu.common.mvp.PresenterLifeCycle;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;


public class MinePersonalInfoActivity extends BaseActivity implements MinePersonalInfoViewer, View.OnClickListener {

    private List<String> listData = new ArrayList<>();
    private List<String> picList = new ArrayList<>();
    private List<MineLocationDynamicBean> list = new ArrayList<>();
    private DialogUtils reportDialog;
    private List<LinearLayout> llList = new ArrayList<>();
    private List<LinearLayout> llRootList = new ArrayList<>();
    private List<TextView> tvList = new ArrayList<>();
    private List<View> viewList = new ArrayList<>();
    private MZBannerView mBanner;

    @PresenterLifeCycle
    private MinePersonalInfoPresenter presenter = new MinePersonalInfoPresenter(this);
    private LinearLayout ll_first;
    private LinearLayout ll_second;
    private LinearLayout ll_third;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_personal_info_view);
    }

    @Override
    protected void loadData() {
        for (int i = 0; i < 7; i++) {
            listData.add("");
        }


        mBanner = bindView(R.id.banner);
        LinearLayout ll_root_frist = bindView(R.id.ll_root_frist);
        LinearLayout ll_root_second = bindView(R.id.ll_root_second);
        LinearLayout ll_root_third = bindView(R.id.ll_root_third);
        ll_first = bindView(R.id.ll_first);
        ll_second = bindView(R.id.ll_second);
        ll_third = bindView(R.id.ll_third);
        TextView tv_first = bindView(R.id.tv_first);
        TextView tv_second = bindView(R.id.tv_second);
        TextView tv_third = bindView(R.id.tv_third);
        View view_first = bindView(R.id.view_first);
        View view_second = bindView(R.id.view_second);
        View view_third = bindView(R.id.view_third);

        ll_first.setOnClickListener(this);
        ll_second.setOnClickListener(this);
        ll_third.setOnClickListener(this);
        llRootList.add(ll_root_frist);
        llRootList.add(ll_root_second);
        llRootList.add(ll_root_third);
        llList.add(ll_first);
        llList.add(ll_second);
        llList.add(ll_third);
        tvList.add(tv_first);
        tvList.add(tv_second);
        tvList.add(tv_third);
        viewList.add(view_first);
        viewList.add(view_second);
        viewList.add(view_third);


        RecyclerView rv_dynamic = bindView(R.id.rv_dynamic);
        rv_dynamic.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView rv_video_photo = bindView(R.id.rv_video_photo);
        rv_video_photo.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv_video_photo.addItemDecoration(new ScreenSpaceItemDecoration(getActivity(), 4, 2));
        setTypeCheck(ll_first);
        //视频/照片
        MineInfoDataRvAdapter adapter = new MineInfoDataRvAdapter(R.layout.item_info_data, listData, getActivity());

        rv_video_photo.setAdapter(adapter);

        //动态
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426293040&di=8f9de00e1862ebe58e47f7e1a519517c&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201307%2F20%2F153912fo4kxx5kjo6zv52v.jpg");
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426293038&di=83f23fd192c537f42afab17a732883c4&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201707%2F24%2F20170724102404_wAjaP.png");
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426293040&di=ff3ae65767bd9954ef2e220abc2383e8&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F4%2F5696f5a8a6eb3.jpg%3Fdown");
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426293039&di=03d1fc1a8105f7d1e4ec80f6685a964a&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F010c2658ea1351a8012049efead301.jpg%401280w_1l_2o_100sh.jpg");
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426293039&di=410ac577370998d1813ee18a2a88b0f4&imgtype=0&src=http%3A%2F%2Fgss0.baidu.com%2F9vo3dSag_xI4khGko9WTAnF6hhy%2Fzhidao%2Fpic%2Fitem%2F0df3d7ca7bcb0a463350c11e6863f6246b60afa3.jpg");


        for (int i = 0; i < 10; i++) {
            //title
            MineLocationDynamicBean mineLocationDynamicTitleBean = new MineLocationDynamicBean();
            mineLocationDynamicTitleBean.itemType = 0;
            list.add(mineLocationDynamicTitleBean);

            if (i != 0) {
                //pic
                MineLocationDynamicBean mineLocationDynamicPicBean = new MineLocationDynamicBean();
                mineLocationDynamicPicBean.pictures = picList;
                mineLocationDynamicPicBean.itemType = 1;
                list.add(mineLocationDynamicPicBean);
            } else {
                //video
                MineLocationDynamicBean mineLocationDynamicVideoBean = new MineLocationDynamicBean();
                mineLocationDynamicVideoBean.video_pict_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426510803&di=2bf93542ebc2b9a183695626fbffb5de&imgtype=0&src=http%3A%2F%2Fwww.desktx.cc%2Fd%2Ffile%2Fphone%2Fkatong%2F20161203%2F61ad328e4d8c741437ed00209a6bae35.jpg";
                mineLocationDynamicVideoBean.itemType = 2;
                list.add(mineLocationDynamicVideoBean);
            }

            //bottom
            MineLocationDynamicBean mineLocationDynamicBottomBean = new MineLocationDynamicBean();
            mineLocationDynamicBottomBean.itemType = 3;
            list.add(mineLocationDynamicBottomBean);
        }

        MineInfoDynamicRvAdapter adapterDynamic = new MineInfoDynamicRvAdapter(list, getActivity());
        rv_dynamic.setAdapter(adapterDynamic);
        adapterDynamic.setOnItemDetailsDoCilckListener(new MineInfoDynamicRvAdapter.OnItemDetailsDoCilckListener() {
            @Override
            public void onItemDetailsReportClick() {
                showReportDialog();
            }
        });




        initBanner(picList);


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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_first:
                setTypeCheck(ll_first);
                break;
            case R.id.ll_second:
                setTypeCheck(ll_second);
                break;
            case R.id.ll_third:
                setTypeCheck(ll_third);
                break;
        }
    }

    /**
     * 举报弹窗
     */
    private void showReportDialog() {
        reportDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_normal)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .style(R.style.Dialog)
                .settext("请选择", R.id.tv_title)
                .settext("举报", R.id.tv_bottom)
                .addViewOnclick(R.id.tv_cancle, view -> {
                    if (reportDialog.isShowing()) {
                        reportDialog.dismiss();
                    }
                })
                .addViewOnclick(R.id.tv_bottom, view -> {
                    if (reportDialog.isShowing()) {
                        reportDialog.dismiss();
                    }

                })
                .build();
        reportDialog.show();
        reportDialog.findViewById(R.id.tv_top).setVisibility(View.GONE);
        reportDialog.findViewById(R.id.view_middle).setVisibility(View.GONE);

    }


    //点击不同对象不同的风格
    private void setTypeCheck(LinearLayout llType) {
        for (int i = 0; i < llList.size(); i++) {
            LinearLayout linearLayout = llList.get(i);
            if (linearLayout.equals(llType)) {
                tvList.get(i).setTextSize(20);
                tvList.get(i).getPaint().setFakeBoldText(true);
                viewList.get(i).setVisibility(View.VISIBLE);
                llRootList.get(i).setVisibility(View.VISIBLE);
            } else {
                tvList.get(i).setTextSize(15);
                tvList.get(i).getPaint().setFakeBoldText(false);
                viewList.get(i).setVisibility(View.INVISIBLE);
                llRootList.get(i).setVisibility(View.GONE);
            }
        }
    }
}
