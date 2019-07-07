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
import com.hzrcht.seaofflowers.data.UserProfile;
import com.hzrcht.seaofflowers.module.dynamic.activity.ReleaseDynamicActivity;
import com.hzrcht.seaofflowers.module.dynamic.adapter.DynamicListRvAdapter;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineDynamicBean;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineLocationDynamicBean;
import com.hzrcht.seaofflowers.module.dynamic.fragment.presenter.DynamicPresenter;
import com.hzrcht.seaofflowers.module.dynamic.fragment.presenter.DynamicViewer;
import com.hzrcht.seaofflowers.module.home.activity.HomeReportActivity;
import com.hzrcht.seaofflowers.utils.DialogUtils;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class DynamicFragment extends BaseFragment implements DynamicViewer, View.OnClickListener {
    private List<MineLocationDynamicBean> list = new ArrayList<>();
    private List<LinearLayout> llList = new ArrayList<>();
    private List<TextView> tvList = new ArrayList<>();
    private List<View> viewList = new ArrayList<>();
    @PresenterLifeCycle
    private DynamicPresenter mPresenter = new DynamicPresenter(this);
    private LinearLayout ll_recommend;
    private LinearLayout ll_attention;
    private DialogUtils reportDialog, commentDialog;
    private RecyclerView mDynamic;

    private int page = 0;
    private int pageSize = 10;
    private DynamicListRvAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_dynamic_view;
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
        ll_recommend = bindView(R.id.ll_recommend);
        ll_attention = bindView(R.id.ll_attention);
        TextView tv_recommend = bindView(R.id.tv_recommend);
        TextView tv_attention = bindView(R.id.tv_attention);
        View view_recommend = bindView(R.id.view_recommend);
        View view_attention = bindView(R.id.view_attention);
        mDynamic = bindView(R.id.rv_dynamic);

        llList.add(ll_recommend);
        llList.add(ll_attention);
        tvList.add(tv_recommend);
        tvList.add(tv_attention);
        viewList.add(view_recommend);
        viewList.add(view_attention);

        ll_recommend.setOnClickListener(this);
        ll_attention.setOnClickListener(this);


        mDynamic.setLayoutManager(new LinearLayoutManager(getActivity()));


        setTypeCheck(ll_recommend);

        mPresenter.getStateList("", page, pageSize);
    }

    /**
     * 举报弹窗
     */
    private void showReportDialog(String anchor_id, String state_id) {
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
                    Bundle bundle = new Bundle();
                    bundle.putString("ANCHOR_ID", anchor_id);
                    bundle.putString("STATE_ID", state_id);
                    LauncherHelper.from(getActivity()).startActivity(HomeReportActivity.class, bundle);

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
                page = 1;
                setTypeCheck(ll_recommend);
                mDynamic.scrollToPosition(0);
                mPresenter.getStateList("", page, pageSize);
                break;
            case R.id.ll_attention:
                page = 1;
                mDynamic.scrollToPosition(0);
                setTypeCheck(ll_attention);
                mPresenter.getStateList("0", page, pageSize);
                break;
        }
    }

    @Override
    public void getStateListSuccess(MineDynamicBean mineDynamicBean) {
        if (mineDynamicBean != null) {
            if (mineDynamicBean.rows != null && mineDynamicBean.rows.size() != 0) {
                if (page > 1) {

                } else {
                    list.clear();
                }
                for (int i = 0; i < mineDynamicBean.rows.size(); i++) {
                    MineDynamicBean.RowsBean rowsBean = mineDynamicBean.rows.get(i);
                    //title
                    MineLocationDynamicBean mineLocationDynamicTitleBean = new MineLocationDynamicBean();
                    mineLocationDynamicTitleBean.userInfo = rowsBean.userInfo;
                    mineLocationDynamicTitleBean.title = rowsBean.title;
                    mineLocationDynamicTitleBean.create_at = rowsBean.create_at;
                    mineLocationDynamicTitleBean.itemType = 0;
                    list.add(mineLocationDynamicTitleBean);

                    if (rowsBean.is_video == 0) {
                        //pic
                        MineLocationDynamicBean mineLocationDynamicPicBean = new MineLocationDynamicBean();
                        mineLocationDynamicPicBean.imgs = rowsBean.imgs;
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
                    mineLocationDynamicBottomBean.like_count = rowsBean.like_count;
                    mineLocationDynamicBottomBean.is_like = rowsBean.is_like;
                    mineLocationDynamicBottomBean.review_count = rowsBean.review_count;
                    mineLocationDynamicBottomBean.is_attent = rowsBean.is_attent;
                    mineLocationDynamicBottomBean.id = rowsBean.id;
                    mineLocationDynamicBottomBean.userInfo = rowsBean.userInfo;

                    mineLocationDynamicBottomBean.itemType = 3;
                    list.add(mineLocationDynamicBottomBean);
                }

                adapter = new DynamicListRvAdapter(list, getActivity());
                mDynamic.setAdapter(adapter);

                adapter.setOnItemDetailsDoCilckListener(new DynamicListRvAdapter.OnItemDetailsDoCilckListener() {
                    @Override
                    public void onItemDetailsReportClick(String anchor_id, String state_id) {
                        showReportDialog(anchor_id, state_id);
                    }

                    @Override
                    public void onItemDetailsCommentClick() {
                        showCommentDialog();
                    }

                    @Override
                    public void onItemDetailsLikeClick(int state_id, MineLocationDynamicBean item) {
                        mPresenter.stateLike(state_id + "", item);
                    }
                });
            }
        }

    }

    @Override
    public void stateLikeSuccess(MineLocationDynamicBean item) {
        if (item.is_like == 0) {
            ToastUtils.show("点赞成功");
            item.is_like = 1;
            item.like_count = item.like_count + 1;
        } else {
            ToastUtils.show("取消点赞成功");
            item.is_like = 0;
            item.like_count = item.like_count - 1;
        }
        adapter.notifyDataSetChanged();
    }
}
