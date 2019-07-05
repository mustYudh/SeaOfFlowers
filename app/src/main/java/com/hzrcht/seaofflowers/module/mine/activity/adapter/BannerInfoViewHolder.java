package com.hzrcht.seaofflowers.module.mine.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.hzrcht.seaofflowers.R;
import com.yu.common.glide.ImageLoader;
import com.zhouwei.mzbanner.holder.MZViewHolder;

/**
 * Created by mks on 2019/4/1.
 */

public class BannerInfoViewHolder implements MZViewHolder<String> {
    private ImageView mImageView;

    @Override
    public View createView(Context context) {
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner, null);
        mImageView = (ImageView) view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(Context context, int position, String data) {
        // 数据绑定
        ImageLoader.getInstance().displayImage(mImageView, data, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
