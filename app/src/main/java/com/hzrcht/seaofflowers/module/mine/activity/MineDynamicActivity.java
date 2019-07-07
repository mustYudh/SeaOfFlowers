package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineDynamicBean;
import com.hzrcht.seaofflowers.module.dynamic.bean.MineLocationDynamicBean;
import com.hzrcht.seaofflowers.module.mine.activity.adapter.MineDynamicRvAdapter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineDynamicPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineDynamicViewer;
import com.hzrcht.seaofflowers.utils.DialogUtils;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class MineDynamicActivity extends BaseBarActivity implements MineDynamicViewer {
    private List<MineLocationDynamicBean> list = new ArrayList<>();
    private RecyclerView mDynamic;
    private DialogUtils commentDialog, delDialog;
    private int page = 0;
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
        mPresenter.getStateList("", page, pageSize);
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

                adapter = new MineDynamicRvAdapter(list, getActivity());
                mDynamic.setAdapter(adapter);

                adapter.setOnItemDetailsDoCilckListener(new MineDynamicRvAdapter.OnItemDetailsDoCilckListener() {
                    @Override
                    public void onItemDetailsDelClick(int state_id, int position) {
                        showDelDialog(state_id, position);
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
    public void stateDelSuccess(int position) {
        adapter.getData().remove(position);
        adapter.getData().remove(position - 1);
        adapter.getData().remove(position - 2);
        adapter.notifyDataSetChanged();
    }
}
