package com.hzrcht.seaofflowers.module.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.home.bean.HomeBannerBean;
import com.shehuan.niv.NiceImageView;
import com.yu.common.glide.ImageLoader;
import com.zhouwei.mzbanner.holder.MZViewHolder;

/**
 * Created by mks on 2019/4/1.
 */

public class BannerViewHolder implements MZViewHolder<HomeBannerBean.RowsBean> {
    private NiceImageView mImageView;

    @Override
    public View createView(Context context) {
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner, null);
        mImageView = (NiceImageView) view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(Context context, int position, HomeBannerBean.RowsBean data) {
        // 数据绑定
        ImageLoader.getInstance().displayImage(mImageView, data.imageUrl, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
