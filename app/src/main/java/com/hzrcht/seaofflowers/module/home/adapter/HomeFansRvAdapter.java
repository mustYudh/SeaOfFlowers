package com.hzrcht.seaofflowers.module.home.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class HomeFansRvAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;

    public HomeFansRvAdapter(int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
