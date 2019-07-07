package com.hzrcht.seaofflowers.module.mine.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.home.bean.HomeAttentionBean;
import com.hzrcht.seaofflowers.module.mine.activity.MinePersonalInfoActivity;
import com.yu.common.glide.ImageLoader;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.ui.CircleImageView;

import java.util.List;

public class MineAttentionRvAdapter extends BaseQuickAdapter<HomeAttentionBean.RowsBean, BaseViewHolder> {
    private Context context;

    public MineAttentionRvAdapter(int layoutResId, @Nullable List<HomeAttentionBean.RowsBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeAttentionBean.RowsBean item) {
        CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
        helper.setText(R.id.tv_nickname, item.userInfo.nick_name);
        helper.setText(R.id.tv_sign, TextUtils.isEmpty(item.userInfo.sign) ? "这个人太忙，忘记签名了" : item.userInfo.sign);

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
        ImageLoader.getInstance().displayImage(iv_headimg, item.userInfo.head_img, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);
        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("USER_ID", item.userInfo.id + "");
            LauncherHelper.from(context).startActivity(MinePersonalInfoActivity.class, bundle);
        });
    }
}
