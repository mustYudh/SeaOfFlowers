package com.hzrcht.seaofflowers.module.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.home.bean.HomeSearchBean;
import com.hzrcht.seaofflowers.module.mine.activity.MinePersonalInfoActivity;
import com.yu.common.glide.ImageLoader;
import com.yu.common.launche.LauncherHelper;
import com.yu.common.ui.CircleImageView;

import java.util.List;

public class HomeSearchRvAdapter extends BaseQuickAdapter<HomeSearchBean.RowBean, BaseViewHolder> {
    private Context context;

    public HomeSearchRvAdapter(int layoutResId, @Nullable List<HomeSearchBean.RowBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeSearchBean.RowBean item) {
        CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
        ImageLoader.getInstance().displayImage(iv_headimg, item.cover, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);
        helper.setText(R.id.tv_name, item.nick_name);
        helper.setText(R.id.tv_fans, "粉丝：" + item.fans);
        helper.getView(R.id.ll_root).setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("USER_ID", item.id + "");
            LauncherHelper.from(context).startActivity(MinePersonalInfoActivity.class, bundle);
        });

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
    }
}
