package com.hzrcht.seaofflowers.module.mine.activity.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.mine.activity.bean.PhotoAlbumBean;
import com.yu.common.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;

public class MineInfoDataRvAdapter extends BaseQuickAdapter<PhotoAlbumBean.RowsBean, BaseViewHolder> {
    private Context context;
    private List<PhotoAlbumBean.RowsBean> data;
    private List<String> imgList;

    public MineInfoDataRvAdapter(int layoutResId, @Nullable List<PhotoAlbumBean.RowsBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
        this.data = data;
        imgList = new ArrayList<>();
        initDate();
    }

    public void initDate() {
        for (int i = 0; i < data.size(); i++) {
            imgList.add(data.get(i).img_url);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, PhotoAlbumBean.RowsBean item) {
        ImageView iv_img = helper.getView(R.id.iv_img);
        ImageLoader.getInstance().displayImage(iv_img, item.img_url, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);
        helper.getView(R.id.fl_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePreview.getInstance()
                        .setContext(context)
                        .setIndex(helper.getLayoutPosition())
                        .setImageList(imgList)
                        .setShowDownButton(false)
                        .start();
            }
        });
    }
}
