package com.hzrcht.seaofflowers.module.mine.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.mine.activity.bean.SysGiftBean;
import com.yu.common.glide.ImageLoader;

import java.util.List;

public class MineSysGiftRvAdapter extends BaseQuickAdapter<SysGiftBean.ResultBean, BaseViewHolder> {
    private Context context;
    private List<SysGiftBean.ResultBean> list;

    public MineSysGiftRvAdapter(int layoutResId, @Nullable List<SysGiftBean.ResultBean> list, Context context) {
        super(layoutResId, list);
        this.context = context;
        this.list = list;
        // 初始化数据
        initDate();
    }

    // 初始化isSelected的数据
    public void initDate() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).isIs_select = false;
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, SysGiftBean.ResultBean item) {
        helper.setText(R.id.tv_name, item.title);
        helper.setText(R.id.tv_count, item.price + "金币");
        ImageView iv_present = helper.getView(R.id.iv_present);
        ImageLoader.getInstance().displayImage(iv_present, item.img);

        helper.getView(R.id.ll_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDate();

                if (!item.isIs_select) {
                    item.isIs_select = true;
                } else {
                    item.isIs_select = false;
                }

                if (onItemChcekCheckListener != null) {
                    onItemChcekCheckListener.setOnItemChcekCheckClick(item);
                }
            }
        });

        if (item.isIs_select) {
            helper.getView(R.id.ll_root).setBackgroundResource(R.drawable.shape_present_select);
        } else {
            helper.getView(R.id.ll_root).setBackgroundResource(R.drawable.shape_present_normal);
        }
    }

    public interface OnItemChcekCheckListener {
        void setOnItemChcekCheckClick(SysGiftBean.ResultBean resultBean);
    }

    OnItemChcekCheckListener onItemChcekCheckListener;

    public void setOnItemChcekCheckListener(OnItemChcekCheckListener onItemChcekCheckListener) {
        this.onItemChcekCheckListener = onItemChcekCheckListener;
    }
}
