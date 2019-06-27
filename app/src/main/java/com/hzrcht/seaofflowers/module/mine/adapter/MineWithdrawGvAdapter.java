package com.hzrcht.seaofflowers.module.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hzrcht.seaofflowers.R;

import java.util.List;

public class MineWithdrawGvAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;

    public MineWithdrawGvAdapter(List<String> list, Context context) {
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
            view = View.inflate(context, R.layout.item_mine_withdraw, null);
            holder = new ViewHolder();
            holder.tv_money = (TextView) view.findViewById(R.id.tv_money);
            holder.tv_coin = (TextView) view.findViewById(R.id.tv_coin);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }



        return view;
    }

    public class ViewHolder {
        TextView tv_money, tv_coin;
    }
}
