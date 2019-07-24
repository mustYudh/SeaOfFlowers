package com.hzrcht.seaofflowers.module.message.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.mine.bean.MineCallBean;
import com.yu.common.glide.ImageLoader;
import com.yu.common.ui.CircleImageView;

public class MineCallRvAdapter extends BaseQuickAdapter<MineCallBean.RowsBean, BaseViewHolder> {
    private Context context;

    public MineCallRvAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, MineCallBean.RowsBean item) {
        CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
        ImageLoader.getInstance().displayImage(iv_headimg, item.head_img, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);

        helper.setText(R.id.tv_nickname, item.nick_name);
        helper.setText(R.id.tv_time, item.create_at);
        TextView tv_status = helper.getView(R.id.tv_status);
        ImageView iv_click = helper.getView(R.id.iv_click);
        switch (item.is_click) {
            case 0:
                iv_click.setImageResource(item.is_call == 1 ? R.drawable.ic_call_fail_mine : R.drawable.ic_call_fail);
                tv_status.setText("未接听");
                tv_status.setTextColor(Color.parseColor("#FF0000"));
                break;
            case 1:
                iv_click.setImageResource(item.is_call == 1 ? R.drawable.ic_call_success_mine : R.drawable.ic_call_success);
                tv_status.setText("通话时间" + item.stime + "分钟");
                tv_status.setTextColor(Color.parseColor("#999999"));
                break;
        }

    }
}
