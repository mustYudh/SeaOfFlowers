package com.hzrcht.seaofflowers.module.mine.activity.adapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hzrcht.seaofflowers.R;
import com.yu.common.glide.ImageLoader;

import java.util.List;

public class MineRedactDataGvAdapter extends BaseAdapter {
    private Activity context;
    private List<String> list;

    public MineRedactDataGvAdapter(Activity context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list.size() == 4) {
            return list.size();
        }
        return list.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.gv_item, null);
            holder = new ViewHolder();
            holder.ivDemo = (ImageView) convertView.findViewById(R.id.iv_picture_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == list.size()) {
            if (position == 4) {
                holder.ivDemo.setVisibility(View.GONE);
            }

            holder.ivDemo.setImageBitmap(BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.ic_mine_pic_add));
            holder.ivDemo.setOnClickListener(v -> {
                if (onItemChcekCheckListener != null) {
                    onItemChcekCheckListener.setOnItemChcekCheckClick();
                }
            });
        } else {
            ImageLoader.getInstance().displayImage(holder.ivDemo, list.get(position), R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);
            holder.ivDemo.setOnClickListener(v -> {
                if (onItemChcekCheckListener != null) {
                    onItemChcekCheckListener.setOnItemChcekCheckDelClick(position);
                }
            });
        }
        return convertView;
    }

    public class ViewHolder {
        public ImageView ivDemo;
    }

    public interface OnItemChcekCheckListener {
        void setOnItemChcekCheckClick();

        void setOnItemChcekCheckDelClick(int position);
    }

    OnItemChcekCheckListener onItemChcekCheckListener;

    public void setOnItemChcekCheckListener(OnItemChcekCheckListener onItemChcekCheckListener) {
        this.onItemChcekCheckListener = onItemChcekCheckListener;
    }
}
