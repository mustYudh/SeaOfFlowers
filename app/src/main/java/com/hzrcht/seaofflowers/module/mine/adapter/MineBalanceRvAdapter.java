package com.hzrcht.seaofflowers.module.mine.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserBillListBean;

import java.util.List;

public class MineBalanceRvAdapter extends BaseQuickAdapter<UserBillListBean.RowsBean, BaseViewHolder> {
    private Context context;

    public MineBalanceRvAdapter(int layoutResId, @Nullable List<UserBillListBean.RowsBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBillListBean.RowsBean item) {

    }
}
