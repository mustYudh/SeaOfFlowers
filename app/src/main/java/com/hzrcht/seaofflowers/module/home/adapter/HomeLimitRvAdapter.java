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
        ImageLoader.getInstance().displayImage(iv_headimg, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561426293040&di=679d8e36e08df420eeefa0c3eada0718&imgtype=0&src=http%3A%2F%2Fpic17.nipic.com%2F20111023%2F7495075_112834169348_2.jpg", R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);

    }
}
