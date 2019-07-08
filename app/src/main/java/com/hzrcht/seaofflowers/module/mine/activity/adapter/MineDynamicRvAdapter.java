package com.hzrcht.seaofflowers.module.mine.activity.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.dynamic.adapter.DynamicPicGvAdapter;
import com.hzrcht.seaofflowers.module.mine.bean.MineLocationUserDynamicBean;
import com.yu.common.glide.ImageLoader;
import com.yu.common.ui.CircleImageView;
import com.yu.common.ui.NoSlidingGridView;

import java.util.List;

public class MineDynamicRvAdapter extends BaseMultiItemQuickAdapter<MineLocationUserDynamicBean, BaseViewHolder> {
    private Context context;

    public MineDynamicRvAdapter(List<MineLocationUserDynamicBean> data, Context context) {
        super(data);
        addItemType(0, R.layout.item_dynamic_title);
        addItemType(1, R.layout.item_dynamic_pic);
        addItemType(2, R.layout.item_dynamic_video);
        addItemType(3, R.layout.item_mine_dynamic_bottom);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MineLocationUserDynamicBean item) {
        switch (item.itemType) {
            case 0:
                helper.setGone(R.id.tv_talk, false);
                helper.setGone(R.id.ll_age, false);
                CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
                ImageLoader.getInstance().displayImage(iv_headimg, item.userInfo.head_img, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);
                helper.setText(R.id.tv_nickname, item.userInfo.nick_name);
                helper.setText(R.id.tv_title, item.title);
                helper.setText(R.id.tv_time, item.create_at);
                ImageView iv_sex = helper.getView(R.id.iv_sex);
                iv_sex.setImageResource(item.userInfo.sex == 1 ? R.drawable.ic_man_logo : R.drawable.ic_woman_logo);
                helper.getView(R.id.ll_age).setBackgroundResource(item.userInfo.sex == 1 ? R.drawable.shape_mine_man : R.drawable.shape_mine_woman);
                break;
            case 1:
                NoSlidingGridView gv_pic = helper.getView(R.id.gv_pic);
                DynamicPicGvAdapter adapter = new DynamicPicGvAdapter(item.imgs, context);
                gv_pic.setAdapter(adapter);
                break;
            case 2:
                ImageView iv_video = helper.getView(R.id.iv_video);
                ImageLoader.getInstance().displayImage(iv_video, item.video_url, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);
                break;
            case 3:
                helper.setText(R.id.tv_like, item.like_count + "");
                helper.setText(R.id.tv_review, item.review_count + "");
                ImageView iv_like = helper.getView(R.id.iv_like);

                //点赞人员信息
                if (item.like_name != null && !TextUtils.isEmpty(item.like_name)) {
                    helper.setGone(R.id.ll_like_name, true);
                    helper.setText(R.id.tv_like_name, item.like_name);
                } else {
                    helper.setGone(R.id.ll_like_name, false);
                }
                iv_like.setImageResource(item.is_like == 0 ? R.drawable.ic_is_like_normal : R.drawable.ic_is_like_select);

                helper.getView(R.id.ll_del).setOnClickListener(view -> {
                    //删除
                    assert onItemDetailsDoCilckListener != null;
                    onItemDetailsDoCilckListener.onItemDetailsDelClick(item.id, helper.getLayoutPosition());
                });

                helper.getView(R.id.ll_comment).setOnClickListener(view -> {
                    //评论
                    assert onItemDetailsDoCilckListener != null;
                    onItemDetailsDoCilckListener.onItemDetailsCommentClick(item.id);
                });

                helper.getView(R.id.ll_like).setOnClickListener(view -> {
                    //点赞
                    assert onItemDetailsDoCilckListener != null;
                    onItemDetailsDoCilckListener.onItemDetailsLikeClick(item.id, item);
                });
                break;
        }
    }

    public interface OnItemDetailsDoCilckListener {
        void onItemDetailsDelClick(int state_id, int position);

        void onItemDetailsCommentClick(int state_id);

        void onItemDetailsLikeClick(int state_id, MineLocationUserDynamicBean item);
    }

    OnItemDetailsDoCilckListener onItemDetailsDoCilckListener;

    public void setOnItemDetailsDoCilckListener(OnItemDetailsDoCilckListener onItemDetailsDoCilckListener) {
        this.onItemDetailsDoCilckListener = onItemDetailsDoCilckListener;
    }
}
