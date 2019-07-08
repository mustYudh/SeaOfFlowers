package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.data.UserProfile;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MineContentRvAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MineDynamicRvAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.bean.ReviewListBean;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineDynamicPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineDynamicViewer;
import com.hzrcht.seaofflowers.module.mine.bean.MineLocationUserDynamicBean;
import com.hzrcht.seaofflowers.module.mine.bean.MineUserDynamicBean;
import com.hzrcht.seaofflowers.utils.DialogUtils;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class MineDynamicActivity extends BaseBarActivity implements MineDynamicViewer {
    private List<MineLocationUserDynamicBean> list = new ArrayList<>();
    private RecyclerView mDynamic;
    private DialogUtils commentDialog, delDialog;
    private int page = 1;
    private int pageSize = 10;
    @PresenterLifeCycle
    private MineDynamicPresenter mPresenter = new MineDynamicPresenter(this);
    private MineDynamicRvAdapter adapter;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_dynamic_view);
    }

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_mine_dynamic_view_bar;
    }

    @Override
    protected void loadData() {
        setTitle("我的动态");
        mDynamic = bindView(R.id.rv_dynamic);
        mDynamic.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPresenter.getStateList(page, pageSize);
    }

    @Override
    public void getStateListSuccess(MineUserDynamicBean mineUserDynamicBean) {
        if (mineUserDynamicBean != null) {
            if (mineUserDynamicBean.rows != null && mineUserDynamicBean.rows.size() != 0) {
                if (page > 1) {

                } else {
                    list.clear();
                }
                for (int i = 0; i < mineUserDynamicBean.rows.size(); i++) {
                    MineUserDynamicBean.RowsBean rowsBean = mineUserDynamicBean.rows.get(i);
                    //title
                    MineLocationUserDynamicBean mineLocationUserDynamicTitleBean = new MineLocationUserDynamicBean();
                    mineLocationUserDynamicTitleBean.userInfo = rowsBean.userInfo;
                    mineLocationUserDynamicTitleBean.title = rowsBean.title;
                    mineLocationUserDynamicTitleBean.create_at = rowsBean.create_at;
                    mineLocationUserDynamicTitleBean.itemType = 0;
                    list.add(mineLocationUserDynamicTitleBean);

                    if (rowsBean.is_video == 0) {
                        //pic
                        MineLocationUserDynamicBean mineLocationUserDynamicPicBean = new MineLocationUserDynamicBean();
                        mineLocationUserDynamicPicBean.imgs = rowsBean.imgs;
                        mineLocationUserDynamicPicBean.itemType = 1;
                        list.add(mineLocationUserDynamicPicBean);
                    } else {
                        //video
                        MineLocationUserDynamicBean mineLocationUserDynamicVideoBean = new MineLocationUserDynamicBean();
                        mineLocationUserDynamicVideoBean.video_url = rowsBean.video_url;
                        mineLocationUserDynamicVideoBean.itemType = 2;
                        list.add(mineLocationUserDynamicVideoBean);
                    }

                    //bottom
                    MineLocationUserDynamicBean mineLocationUserDynamicBottomBean = new MineLocationUserDynamicBean();
                    mineLocationUserDynamicBottomBean.like = rowsBean.like;
                    mineLocationUserDynamicBottomBean.review = rowsBean.review;
                    mineLocationUserDynamicBottomBean.id = rowsBean.id;
                    mineLocationUserDynamicBottomBean.is_like = rowsBean.is_like;
                    mineLocationUserDynamicBottomBean.like_count = rowsBean.like_count;
                    mineLocationUserDynamicBottomBean.review_count = rowsBean.review_count;
                    mineLocationUserDynamicBottomBean.userInfo = rowsBean.userInfo;

                    if (rowsBean.like != null && rowsBean.like.size() != 0) {
                        final StringBuilder temp = new StringBuilder();

                        for (int j = 0; j < rowsBean.like.size(); j++) {
                            temp.append(rowsBean.like.get(j).userinfo.nick_name);
                            if (j < rowsBean.like.size() - 1) {
                                temp.append(",");
                            }
                        }

                        mineLocationUserDynamicBottomBean.like_name = temp.toString().trim();
                    } else {
                        mineLocationUserDynamicBottomBean.like_name = "";
                    }
                    mineLocationUserDynamicBottomBean.itemType = 3;
                    list.add(mineLocationUserDynamicBottomBean);
                }
                if (adapter == null) {
                    adapter = new MineDynamicRvAdapter(list, getActivity());
                    mDynamic.setAdapter(adapter);
                } else {
                    adapter.setNewData(list);
                }

                adapter.setOnItemDetailsDoCilckListener(new MineDynamicRvAdapter.OnItemDetailsDoCilckListener() {
                    @Override
                    public void onItemDetailsDelClick(int state_id, int position) {
                        showDelDialog(state_id, position);
                    }

                    @Override
                    public void onItemDetailsCommentClick(int state_id) {
                        mPresenter.getReviewList(state_id + "", 1, 10);
                    }

                    @Override
                    public void onItemDetailsLikeClick(int state_id, MineLocationUserDynamicBean item) {
                        mPresenter.stateLike(state_id + "", item);
                    }
                });
            }
        }
    }

    /**
     * 删除弹窗
     */
    private void showDelDialog(int state_id, int position) {
        delDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_layout)
                .gravity(Gravity.CENTER)
                .cancelTouchout(true)
                .style(R.style.Dialog_NoAnimation)
                .settext("确认要删除吗?", R.id.dialog_content)
                .addViewOnclick(R.id.cancle, view -> {
                    if (delDialog.isShowing()) {
                        delDialog.dismiss();
                    }

                })
                .addViewOnclick(R.id.down, view -> {
                    if (delDialog.isShowing()) {
                        delDialog.dismiss();
                    }
                    mPresenter.stateDel(state_id + "", position);
                })
                .build();
        delDialog.show();


    }


    /**
     * 评论弹窗
     */
    private void showCommentDialog(ReviewListBean reviewListBean) {
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

        RecyclerView rv_content = commentDialog.findViewById(R.id.rv_content);
        rv_content.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (reviewListBean != null) {
            if (reviewListBean.rows != null && reviewListBean.rows.size() != 0) {
                MineContentRvAdapter contentRvAdapter = new MineContentRvAdapter(R.layout.item_mine_dynamic_content, reviewListBean.rows, getActivity());
                rv_content.setAdapter(contentRvAdapter);
            }
        }


    }

    @Override
    public void stateLikeSuccess(MineLocationUserDynamicBean item) {
        if (item.is_like == 0) {
            ToastUtils.show("点赞成功");
            item.is_like = 1;
            item.like_count = item.like_count + 1;
            if (item.like_name != null && !TextUtils.isEmpty(item.like_name)) {
                item.like_name = item.like_name + "," + UserProfile.getInstance().getUserName();
            } else {
                item.like_name = item.like_name + UserProfile.getInstance().getUserName();
            }
        } else {
            ToastUtils.show("取消点赞成功");
            item.is_like = 0;
            item.like_count = item.like_count - 1;
            if (item.like_name.startsWith(UserProfile.getInstance().getUserName())) {
                item.like_name = item.like_name.replace(UserProfile.getInstance().getUserName(), "");
            } else {
                item.like_name = item.like_name.replace("," + UserProfile.getInstance().getUserName(), "");

            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void stateDelSuccess(int position) {
        adapter.getData().remove(position);
        adapter.getData().remove(position - 1);
        adapter.getData().remove(position - 2);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getReviewListSuccess(ReviewListBean reviewListBean) {
        showCommentDialog(reviewListBean);
    }
}
