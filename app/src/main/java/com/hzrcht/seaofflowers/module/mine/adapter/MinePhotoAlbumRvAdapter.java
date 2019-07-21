package com.hzrcht.seaofflowers.module.mine.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.mine.activity.bean.PhotoAlbumBean;
import com.yu.common.glide.ImageLoader;

public class MinePhotoAlbumRvAdapter extends BaseQuickAdapter<PhotoAlbumBean.RowsBean, BaseViewHolder> {
    private Context context;

    public MinePhotoAlbumRvAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PhotoAlbumBean.RowsBean item) {
        ImageView iv_img = helper.getView(R.id.iv_img);
        ImageLoader.getInstance().displayImage(iv_img, item.img_url, R.drawable.ic_placeholder, R.drawable.ic_placeholder_error);
        helper.setGone(R.id.tv_status, item.status == 0);
        helper.getView(R.id.fl_root).setOnClickListener(view -> {
            if (onItemChcekListener != null) {
                onItemChcekListener.setOnItemCheckClick(item.id, helper.getLayoutPosition());
            }
        });
    }

    public interface OnItemCheckListener {
        void setOnItemCheckClick(int id, int position);
    }

    OnItemCheckListener onItemChcekListener;

    public void setOnItemCheckListener(OnItemCheckListener onItemChcekListener) {
        this.onItemChcekListener = onItemChcekListener;
    }
}
