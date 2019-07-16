package com.hzrcht.seaofflowers.module.dynamic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import com.hzrcht.seaofflowers.module.dynamic.adapter.DynamicListRvAdapter;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineDynamicBean;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineLocationDynamicBean;
import com.hzrcht.seaofflowers.module.dynamic.fragment.presenter.RecommendDynamicPresenter;
import com.hzrcht.seaofflowers.module.dynamic.fragment.presenter.RecommendDynamicViewer;
import com.hzrcht.seaofflowers.module.home.activity.HomeReportActivity;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MineContentRvAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.bean.ReviewListBean;
import com.hzrcht.seaofflowers.utils.DialogUtils;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class RecommendDynamicFragment extends BaseFragment implements RecommendDynamicViewer {
    private int page = 0;
    private int pageSize = 10;
    private DynamicListRvAdapter adapter;
    private DialogUtils reportDialog, commentDialog;
    private RecyclerView mDynamic;
    private List<MineLocationDynamicBean> list = new ArrayList<>();
    @PresenterLifeCycle
    private RecommendDynamicPresenter mPresenter = new RecommendDynamicPresenter(this);

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_recommend_dynamic_view;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        mDynamic = bindView(R.id.rv_dynamic);
        mDynamic.setLayoutManager(new LinearLayoutManager(getActivity()));

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
    private void showCommentDialog(ReviewListBean reviewListBean, MineLocationDynamicBean item, String state_id) {
        commentDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_comment)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .style(R.style.Dialog)
                .build();
        commentDialog.show();

        TextView tv_count = commentDialog.findViewById(R.id.tv_count);
        RecyclerView rv_content = commentDialog.findViewById(R.id.rv_content);
        rv_content.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (reviewListBean != null) {
            if (reviewListBean.rows != null && reviewListBean.rows.size() != 0) {
                MineContentRvAdapter contentRvAdapter = new MineContentRvAdapter(R.layout.item_mine_dynamic_content, reviewListBean.rows, getActivity());
                rv_content.setAdapter(contentRvAdapter);
                tv_count.setText("评论" + reviewListBean.rows.size());
            } else {
                tv_count.setText("评论0");
            }
        }
        EditText et_content = commentDialog.findViewById(R.id.et_content);
        LinearLayout ll_submit = commentDialog.findViewById(R.id.ll_submit);
        ll_submit.setOnClickListener(view -> {
            if (TextUtils.isEmpty(et_content.getText().toString().trim())) {
                ToastUtils.show("请输入评论内容");
                return;
            }
            mPresenter.stateReview(state_id, et_content.getText().toString().trim(), item);
        });

    }

    @Override
    public void getStateListSuccess(MineDynamicBean mineDynamicBean) {
        if (mineDynamicBean != null) {
            if (mineDynamicBean.rows != null && mineDynamicBean.rows.size() != 0) {
                mDynamic.setVisibility(View.VISIBLE);
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

                if (adapter == null) {
                    adapter = new DynamicListRvAdapter(list, getActivity());
                    mDynamic.setAdapter(adapter);
                } else {
                    adapter.setNewData(list);
                }

                adapter.setOnItemDetailsDoCilckListener(new DynamicListRvAdapter.OnItemDetailsDoCilckListener() {
                    @Override
                    public void onItemDetailsReportClick(String anchor_id, String state_id) {
                        showReportDialog(anchor_id, state_id);
                    }

                    @Override
                    public void onItemDetailsCommentClick(int state_id, MineLocationDynamicBean item) {
                        mPresenter.getReviewList(state_id + "", item, 1, 1000);
                    }

                    @Override
                    public void onItemDetailsLikeClick(int state_id, MineLocationDynamicBean item) {
                        mPresenter.stateLike(state_id + "", item);
                    }
                });
            } else {
                if (page == 1) {
                    mDynamic.setVisibility(View.GONE);
                }
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

    @Override
    public void getReviewListSuccess(ReviewListBean reviewListBean, MineLocationDynamicBean item, String state_id) {
        showCommentDialog(reviewListBean, item, state_id);
    }

    @Override
    public void stateReviewSuccess(MineLocationDynamicBean item) {
        if (commentDialog.isShowing()) {
            commentDialog.dismiss();
        }

        item.review_count = item.review_count + 1;
        ToastUtils.show("评论成功，等待审核");
        adapter.notifyDataSetChanged();
    }
}
