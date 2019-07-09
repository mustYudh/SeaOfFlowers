package com.hzrcht.seaofflowers.module.mine.activity.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.mine.activity.bean.GiftListBean;
import com.yu.common.glide.ImageLoader;

import java.util.List;

public class MinePresentRvAdapter extends BaseQuickAdapter<GiftListBean.RowsBean, BaseViewHolder> {
    private Context context;

    public MinePresentRvAdapter(int layoutResId, @Nullable List<GiftListBean.RowsBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GiftListBean.RowsBean item) {
        ImageView iv_present = helper.getView(R.id.iv_present);
        ImageLoader.getInstance().displayImage(iv_present, item.img, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);
        helper.setText(R.id.tv_name, item.gift_title);
        helper.setText(R.id.tv_count, "×" + item.count);
    }
}
