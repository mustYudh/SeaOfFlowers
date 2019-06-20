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
        ImageLoader.getInstance().displayImage(iv_headimg, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561044742584&di=c8c0aae75b4b6bce386c01ce6a19c93b&imgtype=0&src=http%3A%2F%2Fpic.eastlady.cn%2Fuploads%2Ftp%2F201708%2F9999%2F8aa8a85f3b.jpg", R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);

    }
}
