package com.hzrcht.seaofflowers.module.home.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserPhotoListBean;
import com.yu.common.glide.ImageLoader;

/**
 * @author yudneghao
 * @date 2019-06-27
 */
public class ReportSelectPhotoAdapter extends BaseQuickAdapter<UserPhotoListBean, BaseViewHolder> {

  public ReportSelectPhotoAdapter() {
    super(R.layout.gv_item_report, null);
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
