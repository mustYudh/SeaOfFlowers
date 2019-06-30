package com.hzrcht.seaofflowers.module.mine.activity.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MineInfoDataRvAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;

    public MineInfoDataRvAdapter(int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
