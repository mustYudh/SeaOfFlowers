package com.hzrcht.seaofflowers.module.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.home.bean.HomeFansBean;
import com.hzrcht.seaofflowers.module.mine.activity.MineChatActivity;
import com.hzrcht.seaofflowers.module.mine.activity.MinePersonalInfoActivity;
import com.yu.common.glide.ImageLoader;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.ui.CircleImageView;

public class HomeFansRvAdapter extends BaseQuickAdapter<HomeFansBean.RowBean, BaseViewHolder> {
    private Context context;

    public HomeFansRvAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeFansBean.RowBean item) {
        CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
        ImageView iv_vip = helper.getView(R.id.iv_vip);
        iv_vip.setImageResource(item.is_vip ? R.drawable.ic_vip : R.drawable.ic_vip_no);
        ImageView iv_lv = helper.getView(R.id.iv_lv);
        switch (item.recharge) {
            case 1:
                iv_lv.setImageResource(R.drawable.lv1);
                break;
            case 2:
                iv_lv.setImageResource(R.drawable.lv2);
                break;
            case 3:
                iv_lv.setImageResource(R.drawable.lv3);
                break;
            case 4:
                iv_lv.setImageResource(R.drawable.lv4);
                break;
            case 5:
                iv_lv.setImageResource(R.drawable.lv5);
                break;
            case 6:
                iv_lv.setImageResource(R.drawable.lv6);
                break;
            case 7:
                iv_lv.setImageResource(R.drawable.lv7);
                break;
            case 8:
                iv_lv.setImageResource(R.drawable.lv8);
                break;
            case 9:
                iv_lv.setImageResource(R.drawable.lv9);
                break;

        }
        helper.setText(R.id.tv_name, item.nick_name);
        ImageLoader.getInstance().displayImage(iv_headimg, item.head_img, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);
        switch (item.online_type) {
            case 1:
                //在线
                helper.setText(R.id.tv_type, "在线");
                helper.getView(R.id.tv_point).setBackgroundResource(R.drawable.shape_green_point);
                break;
            case 2:
                //离线
                helper.setText(R.id.tv_type, "离线");
                helper.getView(R.id.tv_point).setBackgroundResource(R.drawable.shape_gray_point);
                break;
        }

        helper.getView(R.id.iv_chat).setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("IM_ID", item.id + "");
            LauncherHelper.from(context).startActivity(MineChatActivity.class, bundle);
        });

        helper.getView(R.id.iv_video).setOnClickListener(view -> {
            if (onItemVideoListener != null) {
                onItemVideoListener.onItemVideoClick(item);
            }
        });

        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("USER_ID", item.id + "");
            LauncherHelper.from(context).startActivity(MinePersonalInfoActivity.class, bundle);
        });
    }

    public interface OnItemVideoListener {
        void onItemVideoClick(HomeFansBean.RowBean item);
    }

    OnItemVideoListener onItemVideoListener;

    public void setOnItemVideoListener(OnItemVideoListener onItemVideoListener) {
        this.onItemVideoListener = onItemVideoListener;
    }
}
