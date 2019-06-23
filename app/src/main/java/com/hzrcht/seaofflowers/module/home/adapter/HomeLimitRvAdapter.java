package com.hzrcht.seaofflowers.module.home.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.yu.common.glide.ImageLoader;

import java.util.List;

public class HomeLimitRvAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;

    public HomeLimitRvAdapter(int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView iv_headimg = helper.getView(R.id.iv_headimg);
        ImageLoader.getInstance().displayImage(iv_headimg, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561315490584&di=d2644e10fb10d9db821685ca85f75971&imgtype=0&src=http%3A%2F%2Fi1.bbs.fd.zol-img.com.cn%2Ft_s800x5000%2Fg5%2FM00%2F0B%2F0F%2FChMkJln10OuIHZh8AACJEEEqIzAAAhqsgBp1mIAAIko407.jpg", R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);

    }
}
