package com.hzrcht.seaofflowers.module.dynamic.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserPhotoListBean;
import com.yu.common.glide.ImageLoader;


public class ReleaseDynamicPhotoAdapter extends BaseQuickAdapter<UserPhotoListBean, BaseViewHolder> {

  public ReleaseDynamicPhotoAdapter() {
    super(R.layout.gv_item_release, null);
  }

  @Override protected void convert(BaseViewHolder helper, UserPhotoListBean item) {
    ImageView imageView = helper.getView(R.id.iv_picture_item);
    if (item.canAdd && helper.getPosition() == getItemCount() - 1) {
      imageView.setEnabled(true);
      helper.addOnClickListener(R.id.iv_picture_item);
    } else {
      imageView.setEnabled(false);
      ImageLoader.getInstance().displayImage(imageView, item.url);
      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
  }
}
