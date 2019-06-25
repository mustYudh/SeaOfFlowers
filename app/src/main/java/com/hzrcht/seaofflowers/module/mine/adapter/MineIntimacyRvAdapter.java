package com.hzrcht.seaofflowers.module.mine.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;

import java.util.List;

public class MineIntimacyRvAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;

    public MineIntimacyRvAdapter(int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        helper.setText(R.id.tv_num, helper.getLayoutPosition() + "");
        switch (helper.getLayoutPosition()) {
            case 0:
                helper.setGone(R.id.tv_num, false);
                helper.setGone(R.id.iv_icon, true);
                iv_icon.setImageResource(R.drawable.ic_one);
                break;
            case 1:
                helper.setGone(R.id.tv_num, false);
                helper.setGone(R.id.iv_icon, true);
                iv_icon.setImageResource(R.drawable.ic_two);
                break;
            case 2:
                helper.setGone(R.id.tv_num, false);
                helper.setGone(R.id.iv_icon, true);
                iv_icon.setImageResource(R.drawable.ic_three);
                break;
            default:
                helper.setGone(R.id.tv_num, true);
                helper.setGone(R.id.iv_icon, false);
                break;
        }
    }
}
