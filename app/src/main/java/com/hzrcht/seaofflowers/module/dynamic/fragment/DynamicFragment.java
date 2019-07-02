package com.hzrcht.seaofflowers.module.dynamic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.dynamic.adapter.DynamicListRvAdapter;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineLocationDynamicBean;
import com.hzrcht.seaofflowers.module.dynamic.fragment.presenter.DynamicPresenter;
import com.hzrcht.seaofflowers.module.dynamic.fragment.presenter.DynamicViewer;
import com.hzrcht.seaofflowers.module.home.activity.HomeReportActivity;
import com.hzrcht.seaofflowers.utils.DialogUtils;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class DynamicFragment extends BaseFragment implements DynamicViewer, View.OnClickListener {
    private List<MineLocationDynamicBean> list = new ArrayList<>();
    private List<String> picList = new ArrayList<>();
    private List<LinearLayout> llList = new ArrayList<>();
    private List<TextView> tvList = new ArrayList<>();
    private List<View> viewList = new ArrayList<>();
    @PresenterLifeCycle
    private DynamicPresenter mPresenter = new DynamicPresenter(this);
    private LinearLayout ll_recommend;
    private LinearLayout ll_attention;
    private DialogUtils reportDialog, commentDialog;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_dynamic_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426293040&di=8f9de00e1862ebe58e47f7e1a519517c&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201307%2F20%2F153912fo4kxx5kjo6zv52v.jpg");
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426293038&di=83f23fd192c537f42afab17a732883c4&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201707%2F24%2F20170724102404_wAjaP.png");
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426293040&di=ff3ae65767bd9954ef2e220abc2383e8&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F4%2F5696f5a8a6eb3.jpg%3Fdown");
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426293039&di=03d1fc1a8105f7d1e4ec80f6685a964a&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F010c2658ea1351a8012049efead301.jpg%401280w_1l_2o_100sh.jpg");
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426293039&di=410ac577370998d1813ee18a2a88b0f4&imgtype=0&src=http%3A%2F%2Fgss0.baidu.com%2F9vo3dSag_xI4khGko9WTAnF6hhy%2Fzhidao%2Fpic%2Fitem%2F0df3d7ca7bcb0a463350c11e6863f6246b60afa3.jpg");
        ll_recommend = bindView(R.id.ll_recommend);
        ll_attention = bindView(R.id.ll_attention);
        TextView tv_recommend = bindView(R.id.tv_recommend);
        TextView tv_attention = bindView(R.id.tv_attention);
        View view_recommend = bindView(R.id.view_recommend);
        View view_attention = bindView(R.id.view_attention);
        RecyclerView rv_dynamic = bindView(R.id.rv_dynamic);

        llList.add(ll_recommend);
        llList.add(ll_attention);
        tvList.add(tv_recommend);
        tvList.add(tv_attention);
        viewList.add(view_recommend);
        viewList.add(view_attention);

        setTypeCheck(ll_recommend);

        ll_recommend.setOnClickListener(this);
        ll_attention.setOnClickListener(this);

        rv_dynamic.setLayoutManager(new LinearLayoutManager(getActivity()));

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

        DynamicListRvAdapter adapter = new DynamicListRvAdapter(list, getActivity());
        rv_dynamic.setAdapter(adapter);

        adapter.setOnItemDetailsDoCilckListener(new DynamicListRvAdapter.OnItemDetailsDoCilckListener() {
            @Override
            public void onItemDetailsReportClick() {
                showReportDialog();
            }

            @Override
            public void onItemDetailsCommentClick() {
                showCommentDialog();
            }
        });

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
                    LauncherHelper.from(getActivity()).startActivity(HomeReportActivity.class);

                })
                .build();
        reportDialog.show();
        reportDialog.findViewById(R.id.tv_top).setVisibility(View.GONE);
        reportDialog.findViewById(R.id.view_middle).setVisibility(View.GONE);

    }


    /**
     * 评论弹窗
     */
    private void showCommentDialog() {
        commentDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_comment)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .style(R.style.Dialog)
                .addViewOnclick(R.id.ll_submit, view -> {
                    if (commentDialog.isShowing()) {
                        commentDialog.dismiss();
                    }

                })
                .build();
        commentDialog.show();


    }

    //点击不同对象不同的风格
    private void setTypeCheck(LinearLayout llType) {
        for (int i = 0; i < llList.size(); i++) {
            LinearLayout linearLayout = llList.get(i);
            if (linearLayout.equals(llType)) {
                tvList.get(i).setTextSize(20);
                viewList.get(i).setVisibility(View.VISIBLE);
            } else {
                tvList.get(i).setTextSize(16);
                viewList.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_recommend:
                setTypeCheck(ll_recommend);
                break;
            case R.id.ll_attention:
                setTypeCheck(ll_attention);
                break;
        }
    }
}
