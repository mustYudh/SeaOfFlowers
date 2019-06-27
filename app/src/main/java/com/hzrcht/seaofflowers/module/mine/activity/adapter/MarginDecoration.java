package com.hzrcht.seaofflowers.module.mine.activity.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.yu.common.utils.DensityUtil;

/**
 * @author yudneghao
 * @date 2019-06-27
 */
public class MarginDecoration extends RecyclerView.ItemDecoration {
  private int margin;
  private int count;

  public MarginDecoration(Context context, int count,int margin) {
    this.margin = DensityUtil.dip2px(context, margin);
    this.count = count;
  }

  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    if (parent.getChildLayoutPosition(view) % count == 0) {
      outRect.set(margin, 0, margin, 0);
    } else {
      outRect.set(0, 0, margin, 0);
    }
  }
}