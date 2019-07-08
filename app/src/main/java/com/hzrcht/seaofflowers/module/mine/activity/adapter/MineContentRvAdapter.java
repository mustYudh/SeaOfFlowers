package com.hzrcht.seaofflowers.module.mine.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.module.mine.activity.bean.ReviewListBean;

import java.util.List;

public class MineContentRvAdapter extends BaseQuickAdapter<ReviewListBean.RowsBean, BaseViewHolder> {
    private Context context;

    public MineContentRvAdapter(int layoutResId, @Nullable List<ReviewListBean.RowsBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ReviewListBean.RowsBean item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_content = helper.getView(R.id.tv_content);
        if (item.reviewInfo != null) {
            tv_name.setText(item.reviewInfo.nick_name + "回复" + item.userInfo.nick_name);
            SpannableString mStyledText = new SpannableString(tv_name.getText().toString().trim());

            MyClickableSpan myClickableSpan = new MyClickableSpan(context, tv_name.getText().toString().trim().substring(item.reviewInfo.nick_name.length(), item.reviewInfo.nick_name.length() + 2));
            mStyledText.setSpan(myClickableSpan, item.reviewInfo.nick_name.length(), item.reviewInfo.nick_name.length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            MovementMethod instance = LinkMovementMethod.getInstance();
            tv_name.setText(mStyledText);
            tv_name.setMovementMethod(instance);
        } else {
            tv_name.setText(item.userInfo.nick_name);
        }
        tv_content.setText("：" + item.title);
    }


    class MyClickableSpan extends ClickableSpan {
        private Context context;
        private String text;

        public MyClickableSpan(Context context, String text) {
            this.context = context;
            this.text = text;
        }                //在这里设置字体的大小，等待各种属性

        @Override
        public void onClick(@NonNull View view) {

        }

        public void updateDrawState(TextPaint ds) {
            ds.setColor(context.getResources().getColor(R.color.color_333333));
            ds.setFakeBoldText(true);
        }
    }
}
