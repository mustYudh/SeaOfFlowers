package com.hzrcht.seaofflowers.module.mine.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserChargeBean;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.ChargeSettingPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.ChargeSettingViewer;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 收费设置
 */
public class ChargeSettingActivity extends BaseBarActivity implements ChargeSettingViewer {
    private String video_amount = "";
    private String lang_amount = "";
    private String look_amount = "";
    @PresenterLifeCycle
    private ChargeSettingPresenter mPresenter = new ChargeSettingPresenter(this);

    private List<BigDecimal> list = new ArrayList<>();

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_charge_setting_view);
    }

    @Override
    protected void loadData() {
        setTitle("收费设置");
        mPresenter.getUserCharge();

        bindView(R.id.tv_commit, view -> {
            mPresenter.userEditCharge(video_amount, lang_amount, look_amount);
        });
    }

    @Override
    public void getUserChargeSuccess(UserChargeBean userChargeBean) {
        if (userChargeBean != null) {
            bindText(R.id.tv_video_amount, userChargeBean.video_amount + "金币");
            bindText(R.id.tv_lang_amount, userChargeBean.lang_amount + "金币");
            bindText(R.id.tv_look_amount, userChargeBean.look_amount + "金币");

            video_amount = userChargeBean.video_amount + "";
            lang_amount = userChargeBean.lang_amount + "";
            look_amount = userChargeBean.look_amount + "";

            if (userChargeBean.video_amount_arr != null && userChargeBean.video_amount_arr.size() != 0) {
                bindView(R.id.ll_video, view -> {
                    initConfigData(userChargeBean.video_amount_arr);
                });
            } else {
                bindView(R.id.ll_video, view -> {
                    ToastUtils.show("获取数据异常");
                });
            }
        }
    }

    @Override
    public void userEditChargeSuccess() {
        finish();
    }

    private void initConfigData(List<BigDecimal> list) {
        OptionsPickerView options = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                bindText(R.id.tv_video_amount, list.get(options1) + "金币");
                video_amount = list.get(options1) + "";
            }
        })

                .setSubmitText("完成")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.WHITE)//标题文字颜色
                .setSubmitColor(Color.parseColor("#9897E7"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#333333"))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                //.setLinkage(false)//设置是否联动，默认true
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .build();
        options.setNPicker(list, null, null);


        options.show();
    }
}
