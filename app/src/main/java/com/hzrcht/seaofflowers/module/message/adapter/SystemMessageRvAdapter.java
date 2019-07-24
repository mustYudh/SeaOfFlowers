package com.hzrcht.seaofflowers.module.message.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.message.bean.UserSysMessageBean;

public class SystemMessageRvAdapter extends BaseQuickAdapter<UserSysMessageBean.RowsBean, BaseViewHolder> {
    private Context context;

    public SystemMessageRvAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserSysMessageBean.RowsBean item) {
        helper.setText(R.id.tv_time, item.create_at);
        helper.setText(R.id.tv_content, item.title);
    }
}
