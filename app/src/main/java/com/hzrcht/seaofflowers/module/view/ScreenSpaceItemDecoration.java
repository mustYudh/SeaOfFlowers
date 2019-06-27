package com.hzrcht.seaofflowers.module.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yu.common.utils.DensityUtil;


public class ScreenSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int num;

    public ScreenSpaceItemDecoration(Context context, int space,int num) {
        this.space = DensityUtil.dip2px(context, space);
        this.num = num;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = 0;
        outRect.top = space;
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        /**
         * 根据params.getSpanIndex()来判断左右边确定分割线
         * 第一列设置左边距为space，右边距为space/2  （第二列反之）
         */
        if (params.getSpanIndex() % num == 0) {
            outRect.left = 0;
        } else {
            outRect.left = space;
        }
    }
}
