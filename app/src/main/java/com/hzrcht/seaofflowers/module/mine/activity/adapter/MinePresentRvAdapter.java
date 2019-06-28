package com.hzrcht.seaofflowers.module.mine.activity.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.yu.common.glide.ImageLoader;

import java.util.List;

public class MinePresentRvAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;

    public MinePresentRvAdapter(int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView iv_present = helper.getView(R.id.iv_present);
        ImageLoader.getInstance().displayImage(iv_present, item, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);
    }
}
