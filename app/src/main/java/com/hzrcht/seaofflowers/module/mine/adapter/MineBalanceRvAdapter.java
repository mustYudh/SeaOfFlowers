package com.hzrcht.seaofflowers.module.mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserBillListBean;

public class MineBalanceRvAdapter extends BaseQuickAdapter<UserBillListBean.RowsBean, BaseViewHolder> {
    private Context context;

    public MineBalanceRvAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBillListBean.RowsBean item) {
        helper.setText(R.id.tv_type, item.remark);
        helper.setText(R.id.tv_time, item.create_at);
        TextView tv_money = helper.getView(R.id.tv_money);
        if (item.type == 1) {
            helper.setText(R.id.tv_money, "-" + item.change_amount);
            tv_money.setTextColor(Color.parseColor("#FF0000"));
        } else {
            helper.setText(R.id.tv_money, "+" + item.change_amount);
            tv_money.setTextColor(Color.parseColor("#099A5E"));
        }
    }
}
