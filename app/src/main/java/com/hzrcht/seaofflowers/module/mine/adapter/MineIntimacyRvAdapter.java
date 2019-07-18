package com.hzrcht.seaofflowers.module.mine.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.mine.activity.bean.AnchorUserInfoBean;
import com.yu.common.glide.ImageLoader;
import com.yu.common.ui.CircleImageView;

import java.util.List;

public class MineIntimacyRvAdapter extends BaseQuickAdapter<AnchorUserInfoBean.NearBean, BaseViewHolder> {
    private Context context;

    public MineIntimacyRvAdapter(int layoutResId, @Nullable List<AnchorUserInfoBean.NearBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AnchorUserInfoBean.NearBean item) {
        helper.setText(R.id.tv_name, item.nick_name);
        helper.setText(R.id.tv_near_num, "亲密度：" + item.near_num);
        CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
        ImageLoader.getInstance().displayImage(iv_headimg, item.head_img, R.drawable.ic_woman_normal, R.drawable.ic_placeholder_error);
        ImageView iv_lv = helper.getView(R.id.iv_lv);
        switch (item.lv) {
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
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        switch (helper.getLayoutPosition()) {
            case 0:
                helper.setGone(R.id.tv_num, false);
                helper.setGone(R.id.iv_icon, true);
                iv_icon.setImageResource(R.drawable.ic_one);
                break;
            case 1:
                helper.setGone(R.id.tv_num, false);
                helper.setGone(R.id.iv_icon, true);
                iv_icon.setImageResource(R.drawable.ic_two);
                break;
            case 2:
                helper.setGone(R.id.tv_num, false);
                helper.setGone(R.id.iv_icon, true);
                iv_icon.setImageResource(R.drawable.ic_three);
                break;
            default:
                helper.setGone(R.id.tv_num, true);
                helper.setGone(R.id.iv_icon, false);
                helper.setText(R.id.tv_num, (helper.getLayoutPosition() + 1) + "");
                break;
        }
    }
}
