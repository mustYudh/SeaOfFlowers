package com.hzrcht.seaofflowers.module.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.mine.bean.SysMoneyBean;

import java.util.List;

public class MineSysMoneyGvAdapter extends BaseAdapter {
    private List<SysMoneyBean.RowsBean> list;
    private Context context;

    public MineSysMoneyGvAdapter(List<SysMoneyBean.RowsBean> list, Context context) {
        this.list = list;
        this.context = context;
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
            view = View.inflate(context, R.layout.item_mine_sys_money, null);
            holder = new ViewHolder();
            holder.tv_money = (TextView) view.findViewById(R.id.tv_money);
            holder.tv_coin = (TextView) view.findViewById(R.id.tv_coin);
            holder.ll_root = (LinearLayout) view.findViewById(R.id.ll_root);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tv_money.setText(list.get(i).key + "元");
        holder.tv_coin.setText(list.get(i).val + "金币");

        if (list.get(i).isIs_select) {
            holder.tv_money.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_coin.setTextColor(context.getResources().getColor(R.color.white));
            holder.ll_root.setBackgroundResource(R.drawable.shape_withdraw_type_select);
        } else {
            holder.ll_root.setBackgroundResource(R.drawable.shape_withdraw_type);
            holder.tv_money.setTextColor(context.getResources().getColor(R.color.color_666666));
            holder.tv_coin.setTextColor(context.getResources().getColor(R.color.color_666666));
        }

        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initDate();

                if (!list.get(i).isIs_select) {
                    list.get(i).isIs_select = true;
                } else {
                    list.get(i).isIs_select = false;
                }

                if (onItemChcekCheckListener != null) {
                    onItemChcekCheckListener.setOnItemChcekCheckClick(list.get(i).key, list.get(i).id + "");
                }
            }
        });
        return view;
    }

    public class ViewHolder {
        TextView tv_money, tv_coin;
        LinearLayout ll_root;
    }

    public interface OnItemChcekCheckListener {
        void setOnItemChcekCheckClick(String key, String id);


    }

    OnItemChcekCheckListener onItemChcekCheckListener;

    public void setOnItemChcekCheckListener(OnItemChcekCheckListener onItemChcekCheckListener) {
        this.onItemChcekCheckListener = onItemChcekCheckListener;
    }
}


