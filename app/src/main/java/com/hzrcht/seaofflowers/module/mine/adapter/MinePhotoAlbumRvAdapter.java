package com.hzrcht.seaofflowers.module.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.mine.activity.ViewBigImageActivity;
import com.yu.common.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MinePhotoAlbumRvAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;
    private List<String> data;

    public MinePhotoAlbumRvAdapter(int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView iv_img = helper.getView(R.id.iv_img);
        ImageLoader.getInstance().displayImage(iv_img, item, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);

        helper.getView(R.id.fl_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("selet", 2);// 2,大图显示当前页数，1,头像，不显示页数
                bundle.putInt("code", helper.getLayoutPosition());//第几张
                bundle.putStringArrayList("imageuri", (ArrayList<String>) data);
                Intent intent = new Intent(context, ViewBigImageActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
}
