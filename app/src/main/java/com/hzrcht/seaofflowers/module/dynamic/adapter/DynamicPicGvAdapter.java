package com.hzrcht.seaofflowers.module.dynamic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hzrcht.seaofflowers.R;
import com.yu.common.glide.ImageLoader;

import java.util.List;

public class DynamicPicGvAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;

    public DynamicPicGvAdapter(List<String> list, Context context) {
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
            view = View.inflate(context, R.layout.item_pic_view, null);
            holder = new ViewHolder();
            holder.thumb_iv = view.findViewById(R.id.thumb_iv);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

//        int width = ScreentUtils.getScreenWidth(context);//固定宽度
//        //宽度固定,然后根据原始宽高比得到此固定宽度需要的高度
//        ViewGroup.LayoutParams para = holder.thumb_iv.getLayoutParams();
//        para.width = (int) (width / 3);
//        para.height = (int) (width / 3);
//        holder.thumb_iv.setLayoutParams(para);

        ImageLoader.getInstance().displayImage(holder.thumb_iv, list.get(i), R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);

        return view;
    }


    class ViewHolder {
        ImageView thumb_iv;
    }
}
