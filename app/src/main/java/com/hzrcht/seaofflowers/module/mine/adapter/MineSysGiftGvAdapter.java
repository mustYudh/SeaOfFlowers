package com.hzrcht.seaofflowers.module.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.mine.activity.bean.SysGiftBean;
import com.yu.common.glide.ImageLoader;

import java.util.List;

public class MineSysGiftGvAdapter extends BaseAdapter {
    private List<SysGiftBean.ResultBean> list;
    private Context context;

    public MineSysGiftGvAdapter(List<SysGiftBean.ResultBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_sys_present, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.tv_count = (TextView) view.findViewById(R.id.tv_count);
            holder.iv_present = (ImageView) view.findViewById(R.id.iv_present);
            holder.ll_root = (LinearLayout) view.findViewById(R.id.ll_root);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tv_name.setText(list.get(i).title);
        holder.tv_count.setText(list.get(i).price + "金币");
        ImageLoader.getInstance().displayImage(holder.iv_present, list.get(i).img);

        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemChcekCheckListener != null) {
                    onItemChcekCheckListener.setOnItemChcekCheckClick(list.get(i));
                }
            }
        });
        return view;
    }

    public class ViewHolder {
        TextView tv_name, tv_count;
        LinearLayout ll_root;
        ImageView iv_present;
    }

    public interface OnItemChcekCheckListener {
        void setOnItemChcekCheckClick(SysGiftBean.ResultBean resultBean);
    }

    OnItemChcekCheckListener onItemChcekCheckListener;

    public void setOnItemChcekCheckListener(OnItemChcekCheckListener onItemChcekCheckListener) {
        this.onItemChcekCheckListener = onItemChcekCheckListener;
    }
}


