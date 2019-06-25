package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineRedactDataPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineRedactDataViewer;
import com.hzrcht.seaofflowers.module.view.MyOneLineView;
import com.yu.common.mvp.PresenterLifeCycle;


public class MineRedactDataActivity extends BaseBarActivity implements MineRedactDataViewer {

    @PresenterLifeCycle
    private MineRedactDataPresenter presenter = new MineRedactDataPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_redact_data_view);
    }

    @Override
    protected void loadData() {
        setTitle("编辑资料");
        MyOneLineView view_nickname = bindView(R.id.view_nickname);
        MyOneLineView view_work = bindView(R.id.view_work);
        MyOneLineView view_mobile = bindView(R.id.view_mobile);
        MyOneLineView view_height = bindView(R.id.view_height);
        MyOneLineView view_age = bindView(R.id.view_age);
        MyOneLineView view_weight = bindView(R.id.view_weight);
        MyOneLineView view_constellation = bindView(R.id.view_constellation);
        MyOneLineView view_city = bindView(R.id.view_city);
        MyOneLineView view_signature = bindView(R.id.view_signature);
        MyOneLineView view_label = bindView(R.id.view_label);

        view_nickname.init("昵称").setTextRight("聊友:21327").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);
        view_work.init("职业").setTextRight("网红").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);
        view_mobile.init("手机号").setTextRight("15158816233").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);
        view_height.init("身高").setTextRight("160CM").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);
        view_age.init("年龄").setTextRight("20岁").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);
        view_weight.init("体重").setTextRight("40kg").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);
        view_constellation.init("星座").setTextRight("双子座").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);
        view_city.init("城市").setTextRight("杭州市").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);
        view_signature.init("个性签名").setTextRight("请输入个性签名(选填)").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);
        view_label.init("形象标签").setTextRight("请输入形象标签(选填)").showRightText(true).showArrow(true).setTextRightColor(R.color.color_CCCCCC).setIvRightIcon(R.drawable.ic_arrow_gray);

    }
}
